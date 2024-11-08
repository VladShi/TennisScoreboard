-- CREATE TABLE IF NOT EXISTS player (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     name varchar(100) UNIQUE
-- );
-- CREATE TABLE IF NOT EXISTS match (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     player_one_id INT,
--     player_two_id INT,
--     winner_id INT,
--     FOREIGN KEY (player_one_id) REFERENCES player(id),
--     FOREIGN KEY (player_two_id) REFERENCES player(id),
--     FOREIGN KEY (winner_id) REFERENCES player(id)
-- );
INSERT into player (id, name) values (default, 'Novak Djokovic');
INSERT into player (id, name) values (default, 'Rafael Nadal');
INSERT into player (id, name) values (default, 'Karen Khachanov');
INSERT into player (id, name) values (default, 'Stefanos Tsitsipas');
INSERT into player (id, name) values (default, 'Alexander Zverev');
INSERT into player (id, name) values (default, 'Carlos Alcaraz');
INSERT into player (id, name) values (default, 'Jannik Sinner');
INSERT into player (id, name) values (default, 'Daniil Medvedev');
INSERT into player (id, name) values (default, 'Andrey Rublev');
INSERT into player (id, name) values (default, 'Roger Federer');

INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 1, 10, 10);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 2, 9, 2);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 4, 7, 4);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 10, 1, 1);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 5, 6, 6);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 6, 5, 5);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 10, 2, 10);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 7, 4, 4);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 7, 10, 7);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 8, 3, 3);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 4, 10, 10);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 9, 2, 2);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 10, 3, 3);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 4, 8, 8);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 10, 5, 5);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 8, 6, 8);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 9, 4, 4);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 10, 6, 10);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 8, 5, 5);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 10, 7, 10);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 10, 8, 10);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 3, 8, 8);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 10, 9, 10);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 1, 8, 8);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 2, 8, 8);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 8, 7, 8);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 10, 4, 10);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 8, 1, 1);
INSERT into match (id, player_one_id, player_two_id, winner_id) values (default, 1, 10, 10);