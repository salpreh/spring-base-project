-- Factions
INSERT INTO factions (id, name) VALUES (1, 'Systems Alliance');
INSERT INTO factions (id, name) VALUES (2, 'Turian Hierarchy');
INSERT INTO factions (id, name) VALUES (3, 'Asari Republics');
ALTER SEQUENCE faction_pk_gen RESTART WITH 4;

-- Planets
INSERT INTO planets (id, name, affiliation_faction_id) VALUES (1, 'Earth', 1);
INSERT INTO planets (id, name, affiliation_faction_id) VALUES (2, 'Mars', 1);
INSERT INTO planets (id, name, affiliation_faction_id) VALUES (3, 'Eden Prime', 1);
INSERT INTO planets (id, name, affiliation_faction_id) VALUES (4, 'Thessia', 3);
ALTER SEQUENCE planet_pk_gen RESTART WITH 5;

-- Persons
INSERT INTO persons (id, age, alias, name, race, birth_planet_id) VALUES (1, 30, 'Shepard', 'John Shepard', 'HUMAN', 1);
INSERT INTO persons (id, age, alias, name, race, birth_planet_id) VALUES (2, 79, 'Cassi', 'Cassi Udo', 'KROGAN', 3);
INSERT INTO persons (id, age, alias, name, race, birth_planet_id) VALUES (3, 40, 'Regi', 'Reginald Cormier', 'QUARIAN', 3);
INSERT INTO persons (id, age, alias, name, race, birth_planet_id) VALUES (4, 27, 'Astara', 'Astara Valkia', 'TURIAN', 3);
ALTER SEQUENCE person_pk_gen RESTART WITH 5;

-- Spaceship Registration
INSERT INTO spaceship_registrations (id, registration_date, registration_number, signature) VALUES (1, '2023-04-04 17:30:00.000000', '0000000012', 'The Benefactor');
ALTER SEQUENCE spaceship_registration_pk_gen RESTART WITH 2;

-- Spaceships
INSERT INTO spaceships (id, name, affiliation_faction_id, port_planet_id, registration_id) VALUES (1, 'Normandy SR-1', 1, 1, 1);
INSERT INTO spaceships (id, name, affiliation_faction_id, port_planet_id, registration_id) VALUES (2, 'Pictor', null, 3, null);
ALTER SEQUENCE spaceship_pk_gen RESTART WITH 3;

-- Assignations
INSERT INTO assignations (id, position, assignation_spaceship_id, assignee_person_id) VALUES (1, 'Commander', 1, 1);
INSERT INTO assignations (id, position, assignation_spaceship_id, assignee_person_id) VALUES (2, 'Strike team', 2, 2);
INSERT INTO assignations (id, position, assignation_spaceship_id, assignee_person_id) VALUES (3, 'Chef', 2, 3);
INSERT INTO assignations (id, position, assignation_spaceship_id, assignee_person_id) VALUES (4, 'Biologist', 2, 4);
ALTER SEQUENCE assignation_pk_gen RESTART WITH 5;
