CREATE TABLE app_user (
  id                    INT
, name                  VARCHAR(128)
, email                 VARCHAR(128)
, nickName              VARCHAR(128)
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

