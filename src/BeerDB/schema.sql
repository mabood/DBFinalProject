CREATE TABLE Brewery (
    breweryName VARCHAR(100) PRIMARY KEY,
    breweryLocation VARCHAR(100),
    imgURL VARCHAR(300));

CREATE TABLE Beer (
    beerID INT PRIMARY KEY AUTO_INCREMENT,
    beerName VARCHAR(100),
    breweryName VARCHAR(100),
    beerABV REAL,
    beerIBU INT,
    imgURL VARCHAR(300),
    FOREIGN KEY (breweryName) REFERENCES Brewery(breweryName)
    ON UPDATE cascade
    ON DELETE cascade);

CREATE TABLE Bar (
    barID INT PRIMARY KEY AUTO_INCREMENT,
    barName VARCHAR(100),
    barLocation VARCHAR(100));

CREATE TABLE Inventory (
    barID INT,
    beerID INT,
    PRIMARY KEY (barID, beerID),
    FOREIGN KEY (barID) REFERENCES Bar(barID)
    ON DELETE CASCADE,
    FOREIGN KEY (beerID) REFERENCES Beer(beerID)
    ON DELETE CASCADE);

CREATE TABLE BeerBuzz (
    commentID INT PRIMARY KEY AUTO_INCREMENT,
    beerID INT,
    rating INT,
    comment VARCHAR(200),
    FOREIGN KEY (beerID) REFERENCES Beer(beerID)
    ON DELETE CASCADE);