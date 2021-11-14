from time import time

import MySQLdb
import numpy as np
import pandas as pd
import tensorflow as tf


# Function: loading all
def get_match_label(match):
    print("--- get_match_label")
    ''' Derives a label for a given match. '''

    # Define variables
    home_goals = match['home_team_goal']
    away_goals = match['away_team_goal']

    label = pd.DataFrame()
    label.loc[0, 'match_api_id'] = match['match_api_id']

    # Identify match label
    if home_goals > away_goals:
        label.loc[0, 'label'] = 1
    if home_goals == away_goals:
        label.loc[0, 'label'] = 0
    if home_goals < away_goals:
        label.loc[0, 'label'] = 2

    # Return label
    return label.loc[0]


# Function: get all player skill index (stats)
def get_fifa_stats(match, player_stats):
    print("--- get_fifa_stats ---")
    ''' Aggregates fifa stats for a given match. '''

    # Define variables
    # match_id = match.match_api_id
    date = match['date']
    players = ['home_player_1', 'home_player_2', 'home_player_3', "home_player_4",
               "home_player_5", "home_player_6", "home_player_7", "home_player_8",
               "home_player_9", "home_player_10", "home_player_11", "away_player_1",
               "away_player_2", "away_player_3", "away_player_4", "away_player_5",
               "away_player_6", "away_player_7", "away_player_8", "away_player_9",
               "away_player_10", "away_player_11"]
    player_stats_new = pd.DataFrame()
    names = []

    # Loop through all players
    for player in players:

        # Get player ID
        player_id = match[player]

        # Get player stats
        stats = player_stats[player_stats.player_api_id == player_id]

        # Identify current stats
        current_stats = stats[stats.date < date].sort_values(by='date', ascending=False)[:1]

        if np.isnan(player_id) == True:
            overall_rating = pd.Series(0)
        else:
            current_stats.reset_index(inplace=True, drop=True)
            overall_rating = pd.Series(current_stats.loc[0, "overall_rating"])
            p1_potential = pd.Series(current_stats.loc[0, "potential"])
            p2_preferred_foot = pd.Series(current_stats.loc[0, "preferred_foot"])
            p3_attacking_work_rate = pd.Series(current_stats.loc[0, "attacking_work_rate"])
            p4_defensive_work_rate = pd.Series(current_stats.loc[0, "defensive_work_rate"])
            p5_crossing = pd.Series(current_stats.loc[0, "crossing"])
            p6_finishing = pd.Series(current_stats.loc[0, "finishing"])
            p7_heading_accuracy = pd.Series(current_stats.loc[0, "heading_accuracy"])
            p8_short_passing = pd.Series(current_stats.loc[0, "short_passing"])
            p9_volleys = pd.Series(current_stats.loc[0, "volleys"])
            p10_dribbling = pd.Series(current_stats.loc[0, "dribbling"])
            p11_curve = pd.Series(current_stats.loc[0, "curve"])
            p12_free_kick_accuracy = pd.Series(current_stats.loc[0, "free_kick_accuracy"])
            p13_long_passing = pd.Series(current_stats.loc[0, "long_passing"])
            p14_ball_control = pd.Series(current_stats.loc[0, "ball_control"])
            p15_acceleration = pd.Series(current_stats.loc[0, "acceleration"])
            p16_sprint_speed = pd.Series(current_stats.loc[0, "sprint_speed"])
            p15_agility = pd.Series(current_stats.loc[0, "agility"])
            p16_reactions = pd.Series(current_stats.loc[0, "reactions"])
            p17_balance = pd.Series(current_stats.loc[0, "balance"])
            p18_shot_power = pd.Series(current_stats.loc[0, "shot_power"])
            p19_jumping = pd.Series(current_stats.loc[0, "jumping"])
            p20_stamina = pd.Series(current_stats.loc[0, "stamina"])
            p21_strength = pd.Series(current_stats.loc[0, "strength"])
            p22_long_shots = pd.Series(current_stats.loc[0, "long_shots"])
            p23_aggression = pd.Series(current_stats.loc[0, "aggression"])
            p24_interceptions = pd.Series(current_stats.loc[0, "interceptions"])
            p25_positioning = pd.Series(current_stats.loc[0, "positioning"])
            p27_vision = pd.Series(current_stats.loc[0, "vision"])
            p28_penalties = pd.Series(current_stats.loc[0, "penalties"])
            p29_marking = pd.Series(current_stats.loc[0, "marking"])
            p30_standing_tackle = pd.Series(current_stats.loc[0, "standing_tackle"])
            p31_gk_diving = pd.Series(current_stats.loc[0, "gk_diving"])
            p32_gk_handling = pd.Series(current_stats.loc[0, "gk_handling"])
            p33_gk_kicking = pd.Series(current_stats.loc[0, "gk_kicking"])
            p34_gk_positioning = pd.Series(current_stats.loc[0, "gk_positioning"])
            p35_gk_reflexes = pd.Series(current_stats.loc[0, "gk_reflexes"])

        # Rename stat
        name = "{}_or".format(player)
        p1_potential_col = "p1_{}".format(player)
        p2_preferred_foot_col = "p2_{}".format(player)
        p3_attacking_work_rate_col = "p3_{}".format(player)
        p4_defensive_work_rate_col = "p4_{}".format(player)
        p5_crossing_col = "p5_{}".format(player)
        p6_finishing_col = "p6_{}".format(player)
        p7_heading_accuracy_col = "p7_{}".format(player)
        p8_short_passing_col = "p8_{}".format(player)
        p9_volleys_col = "p9_{}".format(player)
        p10_dribbling_col = "p10_{}".format(player)
        p11_curve_col = "p11_{}".format(player)
        p12_free_kick_accuracy_col = "p12_{}".format(player)
        p13_long_passing_col = "p13_{}".format(player)
        p14_ball_control_col = "p14_{}".format(player)
        p15_acceleration_col = "p15_{}".format(player)
        p16_sprint_speed_col = "p16_{}".format(player)
        p15_agility_col = "p15_{}".format(player)
        p16_reactions_col = "p16_{}".format(player)
        p17_balance_col = "p17_{}".format(player)
        p18_shot_power_col = "p18_{}".format(player)
        p19_jumping_col = "p19_{}".format(player)
        p20_stamina_col = "p20_{}".format(player)
        p21_strength_col = "p21_{}".format(player)
        p22_long_shots_col = "p22_{}".format(player)
        p23_aggression_col = "p23_{}".format(player)
        p24_interceptions_col = "p24_{}".format(player)
        p25_positioning_col = "p25_{}".format(player)
        p27_vision_col = "p27_{}".format(player)
        p28_penalties_col = "p28_{}".format(player)
        p29_marking_col = "p29_{}".format(player)
        p30_standing_tackle_col = "p30_{}".format(player)
        p31_gk_diving_col = "p31_{}".format(player)
        p32_gk_handling_col = "p32_{}".format(player)
        p33_gk_kicking_col = "p33_{}".format(player)
        p34_gk_positioning_col = "p34_{}".format(player)
        p35_gk_reflexes_col = "p35_{}".format(player)
        names.append(name)
        names.append(p1_potential_col)
        names.append(p2_preferred_foot_col)
        names.append(p3_attacking_work_rate_col)
        names.append(p4_defensive_work_rate_col)
        names.append(p5_crossing_col)
        names.append(p6_finishing_col)
        names.append(p7_heading_accuracy_col)
        names.append(p8_short_passing_col)
        names.append(p9_volleys_col)
        names.append(p10_dribbling_col)
        names.append(p11_curve_col)
        names.append(p12_free_kick_accuracy_col)
        names.append(p13_long_passing_col)
        names.append(p14_ball_control_col)
        names.append(p15_acceleration_col)
        names.append(p16_sprint_speed_col)
        names.append(p15_agility_col)
        names.append(p16_reactions_col)
        names.append(p17_balance_col)
        names.append(p18_shot_power_col)
        names.append(p19_jumping_col)
        names.append(p20_stamina_col)
        names.append(p21_strength_col)
        names.append(p22_long_shots_col)
        names.append(p23_aggression_col)
        names.append(p24_interceptions_col)
        names.append(p25_positioning_col)
        names.append(p27_vision_col)
        names.append(p28_penalties_col)
        names.append(p29_marking_col)
        names.append(p30_standing_tackle_col)
        names.append(p31_gk_diving_col)
        names.append(p32_gk_handling_col)
        names.append(p33_gk_kicking_col)
        names.append(p34_gk_positioning_col)
        names.append(p35_gk_reflexes_col)

        # Aggregate stats
        player_stats_new = pd.concat([player_stats_new, overall_rating, p1_potential, p2_preferred_foot,
                                      p3_attacking_work_rate, p4_defensive_work_rate, p5_crossing,
                                      p6_finishing, p7_heading_accuracy, p8_short_passing, p9_volleys,
                                      p10_dribbling, p11_curve, p12_free_kick_accuracy, p13_long_passing,
                                      p14_ball_control, p15_acceleration, p16_sprint_speed, p15_agility,
                                      p16_reactions, p17_balance, p18_shot_power, p19_jumping, p20_stamina,
                                      p21_strength, p22_long_shots, p23_aggression, p24_interceptions,
                                      p25_positioning, p27_vision, p28_penalties, p29_marking, p30_standing_tackle,
                                      p31_gk_diving, p32_gk_handling, p33_gk_kicking, p34_gk_positioning,
                                      p35_gk_reflexes], axis=1)

    player_stats_new.columns = names
    # player_stats_new['match_api_id'] = match_id

    player_stats_new.reset_index(inplace=True, drop=True)
    player_stats_new.columns = player_stats_new.columns.str.replace('home_player', 'hp')
    player_stats_new.columns = player_stats_new.columns.str.replace('away_player', 'ap')

    # Return player stats
    return player_stats_new.ix[0]


# Function: gets fifa data for all matches
def get_fifa_data(matches, player_stats, path=None, data_exists=False):
    print("--- get_fifa_data ---")

    # Check if fifa data already exists
    if data_exists == True:
        fifa_data = pd.read_pickle(path)
    else:
        print("Collecting fifa data for each match...")
        start = time()

        matches.shape[1]

        # Apply get_fifa_stats for each match
        fifa_data = matches.apply(lambda x: get_fifa_stats(x, player_stats), axis=1)
        fifa_data.shape[1]

        end = time()
        print("Fifa data collected in {:.1f} minutes".format((end - start) / 60))

    # Return fifa_data
    return fifa_data


if __name__ == '__main__':
    # variable declaration
    host_name = 'localhost'
    user_name = 'root'
    pass_word = 'root'
    db_name = 'marbola'
    file_name = '../Data/csv/data_features_dummies.csv'

    # Fetching data
    start = time()
    conn = MySQLdb.connect(host=host_name, user=user_name, passwd=pass_word, db=db_name)

    # Defining the number of jobs to be run in parallel during grid search
    n_jobs = 1  # Insert number of parallel jobs here

    # Fetching required data tables
    # player_data = pd.read_sql("SELECT * FROM marbola.player;", conn)
    # player_data.drop(['updated_at', 'created_at'], axis=1)
    #
    # player_stats_data = pd.read_sql("SELECT * FROM marbola.player_attributes;", conn)
    # player_stats_data.drop(['updated_at', 'created_at'], axis=1)
    #
    # team_data = pd.read_sql("SELECT * FROM marbola.team;", conn)
    # team_data.drop(['updated_at', 'created_at'], axis=1)
    #
    # match_data = pd.read_sql(
    #     "SELECT s.season, concat(s.year, '-', lpad(s.month, 2, 00), '-', lpad(s.date, 2, 00)) as date,"
    #     " l.home_player_1, l.home_player_2, l.home_player_3, l.home_player_4,"
    #     " l.home_player_5, l.home_player_6, l.home_player_7, l.home_player_8,"
    #     " l.home_player_9, l.home_player_10, l.home_player_11, l.away_player_1,"
    #     " l.away_player_2, l.away_player_3, l.away_player_4, l.away_player_5,"
    #     " l.away_player_6, l.away_player_7, l.away_player_8, l.away_player_9,"
    #     " l.away_player_10, l.away_player_11"
    #     " FROM marbola.schedule s"
    #     " LEFT JOIN marbola.lineup l ON s.id=l.schedule_id WHERE s.id=1;", conn)
    #
    # match_data_v2 = pd.read_sql("SELECT season, date,"
    #                             " home_player_1, home_player_2, home_player_3,"
    #                             " home_player_4, home_player_5, home_player_6, home_player_7,"
    #                             " home_player_8, home_player_9, home_player_10, home_player_11,"
    #                             " away_player_1, away_player_2, away_player_3, away_player_4,"
    #                             " away_player_5, away_player_6, away_player_7, away_player_8,"
    #                             " away_player_9, away_player_10, away_player_11"
    #                             " FROM marbola.match;", conn)
    #
    # rows = ["season", "date",
    #         "home_player_1", "home_player_2", "home_player_3",
    #         "home_player_4", "home_player_5", "home_player_6", "home_player_7",
    #         "home_player_8", "home_player_9", "home_player_10", "home_player_11",
    #         "away_player_1", "away_player_2", "away_player_3", "away_player_4",
    #         "away_player_5", "away_player_6", "away_player_7", "away_player_8",
    #         "away_player_9", "away_player_10", "away_player_11"]
    # match_data_v2.dropna(subset=rows, how='any', inplace=True)
    # match_data_v2 = match_data_v2.head(500)
    #
    # frames = [match_data, match_data_v2]
    #
    # match_data = pd.concat(frames)
    #
    # fifa_data = get_fifa_data(match_data, player_stats_data, data_exists=False)
    # final_data = pd.get_dummies(fifa_data, columns=['p2_hp_1', 'p3_hp_1', 'p4_hp_1', 'p2_hp_2', 'p3_hp_2', 'p4_hp_2',
    #                                                 'p2_hp_3', 'p3_hp_3', 'p4_hp_3', 'p2_hp_4', 'p3_hp_4', 'p4_hp_4',
    #                                                 'p2_hp_5', 'p3_hp_5', 'p4_hp_5', 'p2_hp_6', 'p3_hp_6', 'p4_hp_6',
    #                                                 'p2_hp_7', 'p3_hp_7', 'p4_hp_7', 'p2_hp_8', 'p3_hp_8', 'p4_hp_8',
    #                                                 'p2_hp_9', 'p3_hp_9', 'p4_hp_9', 'p2_hp_10', 'p3_hp_10',
    #                                                 'p4_hp_10',
    #                                                 'p2_hp_11', 'p3_hp_11', 'p4_hp_11',
    #                                                 'p2_ap_1', 'p3_ap_1', 'p4_ap_1', 'p2_ap_2', 'p3_ap_2', 'p4_ap_2',
    #                                                 'p2_ap_3', 'p3_ap_3', 'p4_ap_3', 'p2_ap_4', 'p3_ap_4', 'p4_ap_4',
    #                                                 'p2_ap_5', 'p3_ap_5', 'p4_ap_5', 'p2_ap_6', 'p3_ap_6', 'p4_ap_6',
    #                                                 'p2_ap_7', 'p3_ap_7', 'p4_ap_7', 'p2_ap_8', 'p3_ap_8', 'p4_ap_8',
    #                                                 'p2_ap_9', 'p3_ap_9', 'p4_ap_9', 'p2_ap_10', 'p3_ap_10',
    #                                                 'p4_ap_10',
    #                                                 'p2_ap_11', 'p3_ap_11', 'p4_ap_11'])

    # df = pd.read_csv(file_name)

    # print(df.shape)

    # complete_header = []
    # for title in df.columns:
    #     if title == 'label':
    #         continue
    #     else:
    #         complete_header.append(title)
    #
    # print(complete_header)

    # final_data.to_csv('data_final_test_v2.csv', sep=',', encoding='utf-8', index=False)

    final_data = pd.read_csv('data_final_test_v2.csv')
    # print(final_data.shape)
    final_data_x = final_data.head(1)
    final_data_draw = pd.DataFrame({'0': 1.0, '1': 0.0, '2': 0.0}, index=[0])
    final_data_win = pd.DataFrame({'0': 0.0, '1': 1.0, '2': 0.0}, index=[0])
    final_data_lose = pd.DataFrame({'0': 0.0, '1': 0.0, '2': 1.0}, index=[0])

    # final_data_header = []
    # for title in final_data.columns:
    #     print(title)
    #     final_data_header.append(title)
    #
    # print(final_data_header)

    # for item in complete_header:
    #     if item not in final_data_header:
    #         final_data[item] = 0

    with tf.Session() as sess:
        new_saver = tf.train.import_meta_graph('ann_model_v1.meta')
        new_saver.restore(sess, tf.train.latest_checkpoint('./'))

        graph = tf.get_default_graph()

        x = graph.get_tensor_by_name("x:0")
        y = graph.get_tensor_by_name("y:0")
        keep_prob = graph.get_tensor_by_name("keep_prob:0")

        feed_dict_draw = {x: final_data_x, y: final_data_draw, keep_prob: 1.0}
        feed_dict_win = {x: final_data_x, y: final_data_win, keep_prob: 1.0}
        feed_dict_lose = {x: final_data_x, y: final_data_lose, keep_prob: 1.0}

        accuracy = graph.get_tensor_by_name("accuracy:0")

        if sess.run(accuracy, feed_dict_draw) == 1:
            print('Match is Draw')
        elif sess.run(accuracy, feed_dict_win) == 1:
            print('Home Team is Win')
        else:
            print('Away Team is Win')

#
# data = pd.read_csv('data_final_test.csv')
# print(data.shape)
