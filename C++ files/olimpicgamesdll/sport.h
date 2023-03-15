#pragma once
#include <string>

using namespace std;

class Sport {
	string name;
public:
	Sport(string name) : name(name) {}

	string getName()const { return name; }

	//add discipline
};