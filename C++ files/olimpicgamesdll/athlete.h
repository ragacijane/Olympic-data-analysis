#pragma once
#include <string>
#include <iostream>

using namespace std;

class Athlete {
	int id, old, firstYear, weight, height;
	string name;
	char gender;

public:


	Athlete(int id, string name, char gender, int old, int height, int weight, int firstYear = 0) 
		: id(id), name(name), gender(gender), old(old), firstYear(firstYear), weight(weight), height(height) 
	{}

	// add getyear

	int getHeight()const { return height; }

	int getWeight()const { return weight; }

	int getId()const { return id; }

	char getGender()const { return gender; }

	string getName()const { return name; }

	friend ostream& operator<<(ostream& os, Athlete& a) {
		return os << a.id << " " << a.name << " " << a.gender << " " << a.old << " " << a.height << " " << a.weight << endl;
	}
};