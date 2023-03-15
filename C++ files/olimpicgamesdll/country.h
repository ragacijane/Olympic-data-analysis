#pragma once
#include <string>

using namespace std;

class Country {
	string name;
public:
	Country(string name) : name(name) {}
	string getName()const { return name; }
};