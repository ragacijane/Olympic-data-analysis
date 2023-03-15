
#pragma once
#include <exception>

using namespace std;

class GNemaTek : public exception
{
public:
	GNemaTek() :exception("Greska: Nema tekuceg.") {}
};