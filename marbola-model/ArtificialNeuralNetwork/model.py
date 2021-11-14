# Importing required libraries
from math import floor

import numpy as np
import pandas as pd
import tensorflow as tf
from fancyimpute import KNN
from sklearn.preprocessing import OneHotEncoder
from tqdm import tqdm


# Un-comment if you want to remove row(s) with nan value
# notnans = features_dummies_nan.notnull().all(axis=1)
# features_dummies = features_dummies_nan[notnans]


# Function: impute missing value by KNN
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


# Function: build dataset -> features_dummies
def build_data_set(features_dummies_nan):
    # Fill missing value
    features_dummies = impute_missing_values_by_KNN(features_dummies_nan)

    a = features_dummies.shape[1]

    labels = features_dummies.loc[:, 'label']
    features_dummies = features_dummies.drop('label', axis=1)

    b = features_dummies.shape[1]

    print(a)
    print(b)

    return features_dummies, labels


# Function: on hot encoded for labels
def encode_dataset_by_onehot_encoder(labels):
    # One hot encoding for labels
    enc = OneHotEncoder(sparse=False)
    integer_encoded = np.array(labels).reshape(-1)
    integer_encoded = integer_encoded.reshape(len(integer_encoded), 1)
    onehot_encoded = enc.fit_transform(integer_encoded)

    return onehot_encoded


def prepare_train_test_data(features_dummies, onehot_encoded_data):
    train_size = 0.9

    train_cnt = floor(features_dummies.shape[0] * train_size)

    x_train = features_dummies.iloc[0:train_cnt].values
    y_train = onehot_encoded_data[0:train_cnt]
    x_test = features_dummies.iloc[train_cnt:].values
    y_test = onehot_encoded_data[train_cnt:]

    return x_train, x_test, y_train, y_test


def build_artificial_neural_network_model(x, n_input, n_classes, keep_prob):
    n_hidden_1 = 100
    n_hidden_2 = 100
    n_hidden_3 = 100
    # n_hidden_4 = 50

    weights = {
        'h1': tf.Variable(tf.random_normal([n_input, n_hidden_1]), name="h1"),
        'h2': tf.Variable(tf.random_normal([n_hidden_1, n_hidden_2]), name="h2"),
        'h3': tf.Variable(tf.random_normal([n_hidden_2, n_hidden_3]), name="h3"),
        # 'h4': tf.Variable(tf.random_normal([n_hidden_3, n_hidden_4]), name="h4"),
        'out': tf.Variable(tf.random_normal([n_hidden_3, n_classes]), name="outh")
    }

    biases = {
        'b1': tf.Variable(tf.random_normal([n_hidden_1]), name="bias1"),
        'b2': tf.Variable(tf.random_normal([n_hidden_2]), name="bias2"),
        'b3': tf.Variable(tf.random_normal([n_hidden_3]), name="bias3"),
        # 'b4': tf.Variable(tf.random_normal([n_hidden_4]), name="bias4"),
        'out': tf.Variable(tf.random_normal([n_classes]), name="outbias")
    }

    layer_1 = tf.add(tf.matmul(x, weights['h1']), biases['b1'])
    layer_1 = tf.nn.relu(layer_1)
    layer_1 = tf.nn.dropout(layer_1, keep_prob)

    layer_2 = tf.add(tf.matmul(layer_1, weights['h2']), biases['b2'])
    layer_2 = tf.nn.relu(layer_2)
    layer_2 = tf.nn.dropout(layer_2, keep_prob)

    layer_3 = tf.add(tf.matmul(layer_2, weights['h3']), biases['b3'])
    layer_3 = tf.nn.relu(layer_3)
    layer_3 = tf.nn.dropout(layer_3, keep_prob)

    # layer_4 = tf.add(tf.matmul(layer_3, weights['h4']), biases['b4'])
    # layer_4 = tf.nn.relu(layer_4)
    # layer_4 = tf.nn.dropout(layer_4, keep_prob)

    out_layer = tf.matmul(layer_3, weights['out']) + biases['out']
    # final_out = tf.nn.softmax(out_layer)

    return out_layer


def train_artificial_neural_network(features_dummies, encoded_labels, n_input, n_classes, keep_prob):
    training_epochs = 5000
    display_step = 1000
    batch_size = 10

    x = tf.placeholder(tf.float32, [None, n_input], name="x")
    y = tf.placeholder(tf.float32, name="y")

    x_train, x_test, y_train, y_test = prepare_train_test_data(features_dummies, encoded_labels)
    predictions = build_artificial_neural_network_model(x, n_input, n_classes, keep_prob)

    cost = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(logits=predictions, labels=y), name="cost_function")
    optimizer = tf.train.AdamOptimizer(learning_rate=0.0001, name="Adam").minimize(cost)

    with tf.Session() as sess:
        sess.run(tf.global_variables_initializer())
        saver = tf.train.Saver()

        for epoch in tqdm(range(training_epochs)):
            avg_cost = 0.0
            total_batch = int(len(x_train) / batch_size)
            x_batches = np.array_split(x_train, total_batch)
            y_batches = np.array_split(y_train, total_batch)

            for i in range(total_batch):
                batch_x, batch_y = x_batches[i], y_batches[i]
                _, c = sess.run([optimizer, cost], feed_dict={x: batch_x, y: batch_y, keep_prob: 0.8})
                avg_cost += c / total_batch
            if (epoch + 1) % display_step == 0:
                print("Epoch:", '%04d' % (epoch + 1), "cost=", "{:.9f}".format(avg_cost))

        print("Optimization Finished!")
        correct_prediction = tf.equal(tf.argmax(predictions, 1), tf.argmax(y, 1), name="corr_pred")
        accuracy = tf.reduce_mean(tf.cast(correct_prediction, "float"), name="accuracy")
        print('Accuracy: ', sess.run(accuracy, feed_dict={x: x_test, y: y_test}))
        # print("Accuracy:", accuracy.eval({x: x_test, y: y_test, keep_prob: 1.0}))
        saver.save(sess, 'ann_model_v1')


if __name__ == '__main__':
    # Read data
    features_dummies_nan = pd.read_csv('../Data/csv/data_features_dummies.csv', sep=',')

    features_dummies, labels = build_data_set(features_dummies_nan)
    encoded_labels = encode_dataset_by_onehot_encoder(labels)

    keep_prob = tf.placeholder("float", name="keep_prob")
    n_input = features_dummies.shape[1]
    n_classes = encoded_labels.shape[1]

    train_artificial_neural_network(features_dummies, encoded_labels, n_input, n_classes, keep_prob)
