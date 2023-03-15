#pragma once
#include <map>
#include "athlete.h"
#include "discipline.h"
#include "country.h"
#include "eventitem.h"
#include "game.h"


	class OlimpicGames {
		//map<pair<int, string>, Game*> allGames;


	public:

		map<int, Athlete*> allAthletes;

		map<string, Discipline*> allDisciplines;

		map<string, Sport*> allSports;

		map<string, Country*> allCountries;

		map<pair<int, string>, Game*> allGames;

		vector<EventItem*> allEvenets;
		/**
		*Constructor of OlimpicGames
		*@param - No parameters
		*@return - no returning value
		*/
		OlimpicGames( int);

		void parse(int y);

		string getData(string sf, string yf, string tf, string mf, int xy, int typ, string period);
};