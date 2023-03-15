#pragma once
#include "sport.h"

class Discipline {
	string name;
	Sport* sport;
public:
	
	Discipline(string name, Sport* sport) : name(name), sport(sport) {}

	string getName()const { return name; }
	Sport* getSport()const { return sport; }
};