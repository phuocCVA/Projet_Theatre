drop table LesBillets cascade constraints; 
drop table LesDossiers cascade constraints;
drop table LesRepresentations cascade constraints;
drop table LesSpectacles cascade constraints;
drop table LesPlaces cascade constraints;
drop table LesZones cascade constraints;
drop table LesCategories cascade constraints;

delete from LesCategories;
delete from LesBillets;
delete from LesDossiers;
delete from LesRepresentations;
delete from LesSpectacles;
delete from LesPlaces;
delete from LesZones;

CREATE TABLE LesCategories( 
	nomC VARCHAR(20) not null, 
	prix NUMBER (8,2) not null,
	CONSTRAINT cat_c1 PRIMARY KEY(nomC), 
	CONSTRAINT cat_c2 check (prix > 0)      
); 

CREATE TABLE LesZones(
	numZ NUMBER (4), 
	nomC VARCHAR(20),
	CONSTRAINT zones_c1 PRIMARY KEY  (numZ),
	CONSTRAINT zones_c3 check (numZ > 0)         
); 

CREATE TABLE LesPlaces(
	noPlace NUMBER (4), 
	noRang NUMBER (4), 
	numZ NUMBER (4), 
	CONSTRAINT places_c1 PRIMARY KEY  (noPlace, noRang), 
	CONSTRAINT places_c2 FOREIGN KEY (numZ) REFERENCES LesZones (numZ),
	CONSTRAINT places_c3 check (noRang > 0),   
	CONSTRAINT places_c4 check (noPlace > 0)
); 

CREATE TABLE LesSpectacles (
	numS NUMBER (4), 
	nomS VARCHAR(40), 
	CONSTRAINT spec_c1 PRIMARY KEY  (numS),   
	CONSTRAINT spec_c2 check (numS > 0) 
); 

CREATE TABLE LesRepresentations (
	numS NUMBER (4), 
	dateRep DATE,
	CONSTRAINT rep_c1 PRIMARY KEY(numS, dateRep),
	CONSTRAINT rep_c2 FOREIGN KEY (numS) REFERENCES LesSpectacles (numS) 
); 

CREATE TABLE LesDossiers (
	noDossier NUMBER (4), 
	prixTotal NUMBER (6,2),
	CONSTRAINT dossiers_c1 PRIMARY KEY(noDossier)
);

CREATE TABLE LesBillets (
	noSerie NUMBER (4), 
	numS NUMBER (4),  
	dateRep DATE, 
	noPlace NUMBER (4), 
	noRang NUMBER (4), 
	dateEmission DATE, 
	noDossier NUMBER (4), 
	CONSTRAINT billets_c1 PRIMARY KEY (noSerie), 
	CONSTRAINT billets_c2 FOREIGN KEY (numS,dateRep) REFERENCES LesRepresentations (numS,dateRep), 
	CONSTRAINT billets_c3 FOREIGN KEY (noPlace,noRang) REFERENCES LesPlaces (noPlace,noRang),
	CONSTRAINT billets_c4 FOREIGN KEY (noDossier) REFERENCES LesDossiers (noDossier)
); 
