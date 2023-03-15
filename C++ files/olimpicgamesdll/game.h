#pragma once
#include <string>

using namespace std;

class Game {
	int year;
	string type, city;
public:
	Game(int year, string type, string city) : year(year), type(type), city(city) {}

	int getYear()const { return year; }
	string getType()const { return type; }
	string getCity()const { return city; }
};