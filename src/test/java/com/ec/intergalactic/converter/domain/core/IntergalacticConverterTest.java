package com.ec.intergalactic.converter.domain.core;

import org.junit.jupiter.api.Test;

class IntergalacticConverterTest {

    @Test
    public void integrationPersonaTest(){

        String[] input = {
                "glob is I",
                "prok is V",
                "pish is X",
                "tegj is L",
                "glob glob Silver is 34 Credits",
                "glob prok Gold is 57800 Credits",
                "pish pish Iron is 3910 Credits",
                "how much is pish tegj glob glob ?",
                "how many Credits is glob prok Silver ?",
                "how many Credits is glob prok Gold ?",
                "how many Credits is glob prok Iron ?",
                "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
        };

        IntergalacticConverter intergalacticConverter = new IntergalacticConverter();
        for (String line : input) {
            intergalacticConverter.processQuery(line);
        }

    }



}