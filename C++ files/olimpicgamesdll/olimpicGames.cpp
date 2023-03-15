#include "olimpicGames.h"
#include <regex>
#include <fstream>
#include <iostream>

OlimpicGames::OlimpicGames(int y) {
	parse(y);
}

void OlimpicGames::parse(int y){
	string textLine;
	//C:\\Users\\lukat\\OneDrive\\Desktop\\
	
	ifstream input_eventsFile("C:\\Users\\lukat\\OneDrive\\Desktop\\events.txt");
	if (!input_eventsFile.is_open()) {
		cerr << "Could not open the file - '"
			<< "evenets.txt" << "'" << endl;
		return;
	}

	regex eventRegex("([\\d]*) ([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^\\n]*)");

	while (getline(input_eventsFile, textLine)) {
		smatch result;
		if (regex_match(textLine, result, eventRegex)) {
			int year = atoi(result.str(1).c_str());

			if (year == y || y == 0) {
				//	cout << year << endl;
				string type = result.str(2);
				string city = result.str(3);
				string sport = result.str(4);
				string discipline = result.str(5);
				string typeEv = result.str(6);
				string country = result.str(7);
				string ids = result.str(8);
				string medal = result.str(9);
				if (medal == "") medal = "none";

				if (allGames.end() == allGames.find(pair<int, string>(year, type)))
					allGames[pair<int, string>(year, type)] = new Game(year, type, city);

				if (allCountries.end() == allCountries.find(country))
					allCountries[country] = new Country(country);

				if (allSports.end() == allSports.find(sport))
					allSports[sport] = new Sport(sport);

				if (allDisciplines.end() == allDisciplines.find(discipline))
					allDisciplines[discipline] = new Discipline(discipline, allSports[sport]);


				vector<int> participants;

				if (typeEv == "Individual") {
					participants.push_back(atoi(ids.c_str()));
				}
				else {
					regex partRegex("(\\d+)");
					sregex_iterator begin(ids.begin(), ids.end(), partRegex);
					sregex_iterator end;
					while (begin != end) {
						smatch sm = *begin;
						participants.push_back(atoi(sm.str(1).c_str()));
						begin++;
					}
				}
				allEvenets.push_back(new EventItem(allGames[pair<int, string>(year, type)], allSports[sport], allDisciplines[discipline], typeEv, allCountries[country], medal, participants));


				//ADD PARTICIPATION
				/*allparticipations.push_back(p);
				allsports[sport]->addParticipation(p);
				allcountries[compcountry]->addParticipation(p);
				allgames[pair<int, string>(year, type)]->addParticipation(p);*/
			}
		}
	}
	input_eventsFile.close();

	if ( 0 == allEvenets.size()  )
		return;

	regex athleteRegex("([\\d]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)");
	//C:\\Users\\lukat\\OneDrive\\Desktop\\

	ifstream input_athletesFile("C:\\Users\\lukat\\OneDrive\\Desktop\\athletes.txt");
	if (!input_athletesFile.is_open()) {
		cerr << "Could not open the file - '"
			<< "athletes.txt" << "'" << endl;
		return;
	}


	while (getline(input_athletesFile, textLine)) {
		smatch result;
		if (regex_match(textLine, result, athleteRegex)) {
			int id = atoi(result.str(1).c_str());
			string name = result.str(2);
			char gender = result.str(3)[0];
			int age;
			int height;
			int weight;
			if (result.str(4) != "NA") age = atoi(result.str(4).c_str());
			else age = 0;

			if (result.str(5) != "NA") height = atoi(result.str(5).c_str());
			else height = 0;

			if (result.str(6) != "NA") weight = atoi(result.str(6).c_str());
			else weight = 0;

			allAthletes[id] = new Athlete(id, name, gender, age, height, weight);
		}
	}

	input_athletesFile.close();
}

string OlimpicGames::getData(string sportFilter, string yearFilter, string typeFilter, string medalFilter, int xy, int typ, string period)
{
	string str = "none";
	vector<string> disciplins;

	map<string, vector<int>> pieChartDataInput;

	for (const auto& x : allEvenets)
		if ((sportFilter != str && x->getSport()->getName() == sportFilter) || sportFilter == str)
			if ((yearFilter != str && x->getGame()->getYear() == stoi(yearFilter)) || yearFilter == str)
				if ((period != str && x->getGame()->getType() == period) || period == str)
					if ((typeFilter != str && x->getTypeEv() == typeFilter) || typeFilter == str)
						if ((medalFilter != str && x->getMedal() == medalFilter) || medalFilter == str)
					{

						for (const auto& y : x->getParticipants())
							pieChartDataInput[x->getCountry()->getName()].push_back(y);

						sort(pieChartDataInput[x->getCountry()->getName()].begin(), pieChartDataInput[x->getCountry()->getName()].end());

						auto it = unique(pieChartDataInput[x->getCountry()->getName()].begin(), pieChartDataInput[x->getCountry()->getName()].end());

						pieChartDataInput[x->getCountry()->getName()].resize(distance(pieChartDataInput[x->getCountry()->getName()].begin(), it));

						if (find(disciplins.begin(), disciplins.end(), x->getDiscipline()->getName()) == disciplins.end())
							disciplins.push_back(x->getDiscipline()->getName());
					}
	str = "";
	if (!xy) {
		vector<pair<string, int>> pieChartDataOutput;

		for (const auto& x : pieChartDataInput)
			pieChartDataOutput.push_back(pair<string, int>(x.first, x.second.size()));

		sort(pieChartDataOutput.begin(), pieChartDataOutput.end(),
			[](const pair<string, int>& l, const pair<string, int>& r)
		{
			if (l.second != r.second) {
				return l.second > r.second;
			}
			return l.first > r.first;
		});
		if (pieChartDataOutput.size() > 10) {
			for (int i = 10; i < pieChartDataOutput.size(); i++)
				pieChartDataOutput[9].second += pieChartDataOutput[i].second;
			pieChartDataOutput[9].first = "Other";
			pieChartDataOutput.resize(10);
		}
		if (pieChartDataOutput.size() > 0)
			for (auto const& pair : pieChartDataOutput) {

				str += pair.first;
				str.push_back(' ');
				str += to_string(pair.second);
				str.push_back('!');
			}
		else {
			str += "None";
			str.push_back(' ');
			str += '0';
			str.push_back('!');
		}

		str += to_string(pieChartDataOutput.size());
		str.push_back('!');
		int sum = 0;
		for (int i = 0; i < pieChartDataOutput.size(); i++)
			sum += pieChartDataOutput[i].second;
		str += to_string(sum);
		str.push_back('!');
	}
	if (xy == 0 || xy == 1 && typ == 1) {
		str += to_string(disciplins.size());
		str.push_back('!');
	}

	double sum1 = 0, sum2 = 0, cnt1 = 0, cnt2 = 0;
	for (const auto& y : pieChartDataInput) 
		for (const auto& x : y.second) {
		if (allAthletes[x]->getHeight() > 0) {
			sum1 += allAthletes[x]->getHeight();
			cnt1++;
		}
		if (allAthletes[x]->getHeight() > 0) {
			sum2 += allAthletes[x]->getWeight();
			cnt2++;
		}

	}
	if (xy == 0 || xy == 1 && typ == 2) {
		if (sum1 != 0)
			str += to_string(sum1 / cnt1);
		else str += '0';
		str.push_back('!');
	}
	if (xy == 0 || xy == 1 && typ == 3) {
		if (sum2 != 0)
			str += to_string(sum2 / cnt2);
		else str += '0';
		str.push_back('!');
	}
	return str;
}

	
