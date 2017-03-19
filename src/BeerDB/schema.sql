CREATE TABLE Brewery (breweryName VARCHAR(100) PRIMARY KEY ,
                      breweryLocation VARCHAR(100),
                      imgURL VARCHAR(300));

CREATE TABLE Beer (beerID INT PRIMARY KEY,
                   beerName VARCHAR(100),
                   breweryName VARCHAR(100),
                   beerABV REAL,
                   beerIBU INT,
                   FOREIGN KEY (breweryName) REFERENCES Brewery(breweryName));

CREATE TABLE BAR (barID INT PRIMARY KEY,
                  barName VARCHAR(100),
                  barLocation VARCHAR(100));


