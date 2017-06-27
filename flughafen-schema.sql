-- Drop all tables
DROP TABLE IF EXISTS public.mitarbeiter, public.flug, public.fluggaeste, public.fluggesellschaft, public.flughafen, public.gate;

-- Create tables
CREATE TABLE public.flughafen
(
  id VARCHAR(30) PRIMARY KEY NOT NULL UNIQUE
);

CREATE TABLE public.gate
(
  gate_id VARCHAR(30) NOT NULL,
  flughafen VARCHAR(30) NOT NULL,
  FOREIGN KEY (flughafen) REFERENCES flughafen ON DELETE CASCADE,
  PRIMARY KEY (gate_id, flughafen)
);

CREATE TABLE public.mitarbeiter
(
  pers_nr INTEGER PRIMARY KEY,
  name VARCHAR(30) NOT NULL,
  lohn FLOAT,
  rolle VARCHAR(30) NOT NULL,
  arbeitgeber VARCHAR(30) NOT NULL REFERENCES flughafen,
  UNIQUE (pers_nr)
);

CREATE TABLE public.fluggesellschaft
(
  name VARCHAR(30) PRIMARY KEY NOT NULL,
  UNIQUE (name)
);

CREATE TABLE public.flug
(
  flugnummer INTEGER PRIMARY KEY NOT NULL,
  kapazitaet INTEGER CHECK (kapazitaet BETWEEN 2 AND 3000) NOT NULL,
  start VARCHAR(30) NOT NULL REFERENCES flughafen,
  start_time TIMESTAMP NOT NULL,
  start_gate VARCHAR(30) NOT NULL,
  ziel VARCHAR(30) NOT NULL REFERENCES flughafen,
  ziel_time TIMESTAMP NOT NULL,
  ziel_gate VARCHAR(30) NOT NULL,
  flugzeugtyp VARCHAR(30) NOT NULL,
  fluggesellschaft VARCHAR(30) NOT NULL,
  FOREIGN KEY (fluggesellschaft) REFERENCES fluggesellschaft ON DELETE CASCADE,
  UNIQUE (flugnummer),
  FOREIGN KEY (start_gate, start) REFERENCES gate,
  FOREIGN KEY (ziel_gate, ziel) REFERENCES gate
);

CREATE TABLE public.fluggaeste
(
  passport_nr VARCHAR(30) PRIMARY KEY,
  name VARCHAR(30) NOT NULL,
  start VARCHAR(30) NOT NULL REFERENCES flughafen ON DELETE CASCADE,
  ziel VARCHAR(30) NOT NULL REFERENCES flughafen ON DELETE CASCADE,
  UNIQUE (passport_nr)
);

-- Grant priviliges
GRANT SELECT, INSERT, DELETE
ON TABLE flug, fluggaeste, fluggesellschaft,
flughafen, gate, mitarbeiter
TO
_s0556350__flughafen_generic;



-- Insert data
INSERT INTO flughafen (id)
VALUES ('TXL');

INSERT INTO flughafen (id)
VALUES ('SFO');

INSERT INTO flughafen (id)
VALUES ('AKL');

INSERT INTO flughafen (id)
VALUES ('BKK');

INSERT INTO gate (gate_id, flughafen)
VALUES ('1', 'TXL');

INSERT INTO gate (gate_id, flughafen)
VALUES ('2', 'TXL');

INSERT INTO gate (gate_id, flughafen)
VALUES ('3', 'TXL');

INSERT INTO gate (gate_id, flughafen)
VALUES ('1', 'SFO');

INSERT INTO gate (gate_id, flughafen)
VALUES ('2', 'SFO');

INSERT INTO gate (gate_id, flughafen)
VALUES ('3', 'SFO');

INSERT INTO gate (gate_id, flughafen)
VALUES ('1', 'AKL');

INSERT INTO gate (gate_id, flughafen)
VALUES ('2', 'AKL');

INSERT INTO gate (gate_id, flughafen)
VALUES ('1', 'BKK');

INSERT INTO gate (gate_id, flughafen)
VALUES ('2', 'BKK');

INSERT INTO mitarbeiter (pers_nr, name, lohn, rolle, arbeitgeber)
VALUES (35634, 'Hans Otto', 1243.36, 'Sicherheit', 'TXL');

INSERT INTO mitarbeiter (pers_nr, name, lohn, rolle, arbeitgeber)
VALUES (15323, 'John Spencer', 630.24, 'Security', 'SFO');

INSERT INTO mitarbeiter (pers_nr, name, lohn, rolle, arbeitgeber)
VALUES (9485, 'Tainui Hakka', 800.45, 'Security', 'AKL');

INSERT INTO mitarbeiter (pers_nr, name, lohn, rolle, arbeitgeber)
VALUES (98345, 'Somchai May', 200.34, 'ความปลอดภัย', 'BKK');

INSERT INTO fluggesellschaft (name)
VALUES ('Lufthansa');

INSERT INTO fluggesellschaft (name)
VALUES ('United');

INSERT INTO fluggesellschaft (name)
VALUES ('Air New Zealand');

INSERT INTO fluggesellschaft (name)
VALUES ('AirAsia');

INSERT INTO flug (flugnummer, kapazitaet, start, start_time, start_gate, ziel, ziel_time, ziel_gate, flugzeugtyp, fluggesellschaft)
VALUES (3849, 555, 'TXL', TIMESTAMP '2017-08-16 18:36:38', '1', 'SFO', TIMESTAMP '2017-08-17 06:36:38', '2', 'AirBus A380', 'Lufthansa');

INSERT INTO flug (flugnummer, kapazitaet, start, start_time, start_gate, ziel, ziel_time, ziel_gate, flugzeugtyp, fluggesellschaft)
VALUES (1489, 800, 'SFO', TIMESTAMP '2017-08-17 09:29:00', '3', 'AKL', TIMESTAMP '2017-08-18 23:49:23', '2', 'Boeng 747', 'United');

INSERT INTO flug (flugnummer, kapazitaet, start, start_time, start_gate, ziel, ziel_time, ziel_gate, flugzeugtyp, fluggesellschaft)
VALUES (2983, 700, 'AKL', TIMESTAMP '2017-08-19 03:20:23', '1', 'BKK', TIMESTAMP '2017-08-19 14:29:23', '1', 'AirBus A380', 'Air New Zealand');

INSERT INTO flug (flugnummer, kapazitaet, start, start_time, start_gate, ziel, ziel_time, ziel_gate, flugzeugtyp, fluggesellschaft)
VALUES (1431, 600, 'BKK', TIMESTAMP '2017-08-19 18:48:24', '2', 'TXL', TIMESTAMP '2017-08-20 05:23:45', '2', 'Boeing 747', 'AirAsia');

INSERT INTO fluggaeste (passport_nr, name, start, ziel)
VALUES ('DEa7fs979saf798as9ef87', 'Franz Schuhmacher', 'TXL', 'AKL');

INSERT INTO fluggaeste (passport_nr, name, start, ziel)
VALUES ('DE234kkhjnn234njk', 'Ilse Müller', 'SFO', 'BKK');

INSERT INTO fluggaeste (passport_nr, name, start, ziel)
VALUES ('DE2n3kj4nk23nl23nm', 'Günther Kaufmann', 'BKK', 'TXL');

INSERT INTO fluggaeste (passport_nr, name, start, ziel)
VALUES ('DE2lj3n4k23nnkj23', 'Otto Kutscher', 'SFO', 'AKL');