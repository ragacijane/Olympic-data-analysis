#pragma once
#include "discipline.h"
#include "country.h"
#include "athlete.h"
#include "game.h"
#include <vector>

class EventItem {
	string typeEv, medals;
	Sport* sport;
	Discipline* discipline;
	Game* game;
	Country* country;

	vector<int> participants;
public:
	EventItem(Game* game, Sport* sport, Discipline* discipline, string typeEv, Country* country, string medals, vector<int> participants) 
		: game(game), sport(sport), discipline(discipline), typeEv(typeEv), country(country), medals(medals), participants(participants) {}

	friend ostream& operator<<(ostream& os, EventItem& i) {
		return os << i.typeEv << " " << i.medals << endl;
	}

	string getMedal()const { return medals; }
	string getTypeEv()const { return typeEv; }
	Sport* getSport()const { return sport; }
	Discipline* getDiscipline()const { return discipline; }
	Game* getGame()const { return game; }
	Country* getCountry()const { return country; }
	vector<int> getParticipants()const { return participants; }
};