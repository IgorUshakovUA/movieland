CREATE TABLE app_user (
  id                    INT
, name                  VARCHAR(128)
, email                 VARCHAR(128)
, nickName              VARCHAR(128)
, password              VARCHAR(32)
, salt                  VARCHAR(36)
, userrole              VARCHAR(5)
);

CREATE TABLE poster (
  id                    INT
, picturePath           VARCHAR(256)
);

CREATE TABLE genre (
  id                    INT
, name                  VARCHAR(32)
);

CREATE TABLE country (
  id                    INT
, name                  VARCHAR(32)
);

CREATE TABLE countryGroup (
  id                    INT
, countryId             INT
);

CREATE TABLE genreGroup (
  id                    INT
, genreId               INT
);

CREATE TABLE movie (
  id                    INT
, nameRussian           VARCHAR(128)
, nameNative            VARCHAR(128)
, yearOfRelease         INT
, rating                DOUBLE PRECISION
, price                 DOUBLE PRECISION
, description           VARCHAR(1024)
, posterId              INT
, genreGroupId          INT
, countryGroupId        INT
);

CREATE TABLE review (
  id                    INT
, movieId               INT
, userId                INT
, comment               VARCHAR(1024)
);

CREATE SEQUENCE seq_review START 33;

CREATE TABLE userRating(
  userId                INT NOT NULL
, movieId               INT NOT NULL
, rating                DOUBLE PRECISION NOT NULL
);


