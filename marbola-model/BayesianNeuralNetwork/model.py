import sys
from math import floor

import edward as ed
import numpy as np
import pandas as pd
import tensorflow as tf
from edward.models import Normal, Categorical
from fancyimpute import KNN
from sklearn.preprocessing import OneHotEncoder
from tqdm import tqdm


def impute_missing_values_by_KNN(features_dummies_nan):
    home_data = features_dummies_nan[[col for col in features_dummies_nan.columns if 'hp' in col]]
    away_data = features_dummies_nan[[col for col in features_dummies_nan.columns if 'ap' in col]]
    label_data = features_dummies_nan[[col for col in features_dummies_nan.columns if 'label' in col]]

    home_filled = pd.DataFrame(KNN(3).complete(home_data))
    home_filled.columns = home_data.columns
    home_filled.index = home_data.index

    away_filled = pd.DataFrame(KNN(3).complete(away_data))
    away_filled.columns = away_data.columns
    away_filled.index = away_data.index

    data_frame_out = pd.concat([home_filled, away_filled, label_data], axis=1)

    return data_frame_out


def encode_dataset_by_onehot_encoder(labels):
    enc = OneHotEncoder(sparse=False)
    integer_encoded = np.array(labels).reshape(-1)
    integer_encoded = integer_encoded.reshape(len(integer_encoded), 1)
    onehot_encoded = enc.fit_transform(integer_encoded)

    return onehot_encoded


def build_dataset(features_dummies_nan):
    dataset = impute_missing_values_by_KNN(features_dummies_nan)
    dataset = pd.DataFrame(data=dataset)

    data_x = dataset.loc[:, dataset.columns != 'label'].as_matrix().astype(np.float32)
    data_y_ = dataset.loc[:, 'label'].as_matrix().astype(np.float32)
    data_y = encode_dataset_by_onehot_encoder(data_y_)

    return data_x, data_y


def prepare_train_test_data(data_x, data_y):
    train_size = 0.9
    train_cnt = floor(data_x.shape[0] * train_size)

    train_x, test_x = data_x[0:train_cnt], data_x[train_cnt:]
    train_y, test_y = data_y[0:train_cnt], data_y[train_cnt:]

    train_y2 = np.argmax(train_y, axis=1)
    test_y2 = np.argmax(test_y, axis=1)

    return train_x, test_x, train_y2, test_y2, train_cnt


def neural_network(x_, w1, w2, w3, wout, b1, b2, b3, bout):
    l1 = tf.tanh(tf.matmul(x_, w1) + b1)
    # l1 = tf.nn.dropout(l1, keep_prob)

    l2 = tf.tanh(tf.matmul(l1, w2) + b2)
    # l2 = tf.nn.dropout(l2, keep_prob)

    l3 = tf.tanh(tf.matmul(l2, w3) + b3)
    # l3 = tf.nn.dropout(l3, keep_prob)

    l_out = tf.matmul(l3, wout) + bout

    return l_out


def build_bayesian_network(x_, y_, in_size, out_size):
    n_nodes_hl1 = 100
    n_nodes_hl2 = 100
    n_nodes_hl3 = 100

    w_h1 = Normal(loc=tf.zeros([in_size, n_nodes_hl1]),
                  scale=tf.ones([in_size, n_nodes_hl1]))
    w_h2 = Normal(loc=tf.zeros([n_nodes_hl1, n_nodes_hl2]),
                  scale=tf.ones([n_nodes_hl1, n_nodes_hl2]))
    w_h3 = Normal(loc=tf.zeros([n_nodes_hl2, n_nodes_hl3]),
                  scale=tf.ones([n_nodes_hl2, n_nodes_hl3]))
    w_hout = Normal(loc=tf.zeros([n_nodes_hl3, out_size]),
                    scale=tf.ones([n_nodes_hl3, out_size]))
    b_h1 = Normal(loc=tf.zeros([n_nodes_hl1]),
                  scale=tf.ones([n_nodes_hl1]))
    b_h2 = Normal(loc=tf.zeros([n_nodes_hl2]),
                  scale=tf.ones([n_nodes_hl2]))
    b_h3 = Normal(loc=tf.zeros([n_nodes_hl3]),
                  scale=tf.ones([n_nodes_hl3]))
    b_hout = Normal(loc=tf.zeros([out_size]),
                    scale=tf.ones([out_size]))

    y_pre = Categorical(neural_network(x_, w_h1, w_h2, w_h3, w_hout, b_h1, b_h2, b_h3, b_hout))

    qw_h1 = Normal(loc=tf.Variable(tf.random_normal([in_size, n_nodes_hl1])),
                   scale=tf.Variable(tf.random_normal([in_size, n_nodes_hl1])))
    qw_h2 = Normal(loc=tf.Variable(tf.random_normal([n_nodes_hl1, n_nodes_hl2])),
                   scale=tf.Variable(tf.random_normal([n_nodes_hl1, n_nodes_hl2])))
    qw_h3 = Normal(loc=tf.Variable(tf.random_normal([n_nodes_hl2, n_nodes_hl3])),
                   scale=tf.Variable(tf.random_normal([n_nodes_hl2, n_nodes_hl3])))
    qw_hout = Normal(loc=tf.Variable(tf.random_normal([n_nodes_hl3, out_size])),
                     scale=tf.Variable(tf.random_normal([n_nodes_hl3, out_size])))
    qb_h1 = Normal(loc=tf.Variable(tf.random_normal([n_nodes_hl1])),
                   scale=tf.Variable(tf.random_normal([n_nodes_hl1])))
    qb_h2 = Normal(loc=tf.Variable(tf.random_normal([n_nodes_hl2])),
                   scale=tf.Variable(tf.random_normal([n_nodes_hl2])))
    qb_h3 = Normal(loc=tf.Variable(tf.random_normal([n_nodes_hl3])),
                   scale=tf.Variable(tf.random_normal([n_nodes_hl3])))
    qb_hout = Normal(loc=tf.Variable(tf.random_normal([out_size])),
                     scale=tf.Variable(tf.random_normal([out_size])))

    y = Categorical(neural_network(x_, qw_h1, qw_h2, qw_h3, qw_hout, qb_h1, qb_h2, qb_h3, qb_hout))

    inference = ed.KLqp({w_h1: qw_h1, b_h1: qb_h1,
                         w_h2: qw_h2, b_h2: qb_h2,
                         w_h3: qw_h3, b_h3: qb_h3,
                         w_hout: qw_hout, b_hout: qb_hout}, data={y_pre: y_})
    inference.initialize()

    return inference, y_pre, y


def train_bayesian_network(data_x, data_y, in_size, out_size):
    training_epochs = 5000
    display_step = 1000
    batch_size = 10

    # keep_prob = tf.placeholder("float", name="keep_prob")

    x_ = tf.placeholder(tf.float32, shape=(None, in_size))
    y_ = tf.placeholder(tf.int32)

    train_x, test_x, train_y2, test_y2, train_cnt = prepare_train_test_data(data_x, data_y)
    inference, y_pre, y = build_bayesian_network(x_, y_, in_size, out_size)

    sess = tf.Session()
    sess.run(tf.global_variables_initializer())
    with sess:
        samples_num = 100
        for epoch in tqdm(range(training_epochs), file=sys.stdout):
            perm = np.random.permutation(train_cnt)
            for i in range(0, train_cnt, batch_size):
                batch_x = train_x[perm[i:i + batch_size]]
                batch_y = train_y2[perm[i:i + batch_size]]
                inference.update(feed_dict={x_: batch_x, y_: batch_y})
            y_samples = y.sample(samples_num).eval(feed_dict={x_: train_x})
            acc = (np.round(y_samples.sum(axis=0) / samples_num) == train_y2).mean()
            y_samples = y.sample(samples_num).eval(feed_dict={x_: test_x})
            tets_acc = (np.round(y_samples.sum(axis=0) / samples_num) == test_y2).mean()
            if (epoch + 1) % display_step == 0:
                tqdm.write('epoch:\t{}\taccuracy:\t{}\tvaridation accuracy:\t{}'.format(epoch + 1, acc, tets_acc))


if __name__ == '__main__':
    features_dummies_nan = pd.read_csv('csv/features_dummies_with_label.csv', sep=',')

    data_x, data_y = build_dataset(features_dummies_nan)

    in_size = data_x.shape[1]
    out_size = data_y.shape[1]

    train_bayesian_network(data_x, data_y, in_size, out_size)
