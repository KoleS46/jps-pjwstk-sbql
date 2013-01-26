ver. 2.
@SuppressWarnings("rawtypes")	
@SuppressWarnings("unchecked")
po konsultacji z kolega dodanie suppresswarnings kompilacja przebiega bez problemu błąd w implementacji??


ver. 1.
Każda z interfejsów czy to binary, unary, termianl czy auxiliaryname został
zimplementowany za pomoca klasy abstrakcyjnej, która definiuje zgóry zachowanie 
metody i ponieważ na tym etapie projektu metody nie implelemtują jakiś indywidualnych
funkcjonalności dla tych metod. Jeżeli zajdzie taka potrzeba bez problemu będzie można
przesłonić metode odziedziczoną z klasy abstrakcyjnej specyficzną dla danej klasy.

Natomiast nie udało się zaimplementować INameTerminal za pomocą klasy abstakcyjnej
ATermianlExpression implementujacej interfejs ITermianlExpression definujac typ generyczny jako <String>
otrzymuje bład/notice:
"The interface ITerminalExpression cannot be implemented more than once
with different arguments: ITerminalExpression<String> and ITerminalExpression"
Szukanie w googlach ujawniło ze to prawdopodobnie błąd Java7. Której używam.
http://stackoverflow.com/questions/11561770/the-interface-xxxxx-cannot-be-implemented-more-than-once-with-different-argument?answertab=oldest#answer-11562006

NameTermianl został zimpelmentowany prostą metodą poprzez bezpośrednią implementacje interfejsu INameTermianl.

zródł kasy NameTermianl dajaca błąd:

package jpslab.ast.terminal;

import edu.pjwstk.jps.ast.terminal.INameTerminal;

public class NameTerminal extends ATermianlExpression<String> implements INameTerminal{

	public NameTerminal(String ter) {
		super(ter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
