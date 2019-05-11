nextQuestion(kindOfOrganisation) :- kindOfOrganisation(X), var(X).
nextQuestion(kindOfProduct) :- kindOfProduct(X), var(X).
nextQuestion(headOfficeInEU) :- headOfficeInEU(X), var(X).
nextQuestion(weight) :- weight(X), var(X).
nextQuestion(standardPart) :- kindOfProduct(part), standardPart(X), var(X).

nextQuestion(mainResponsabilities) :-
    kindOfOrganisation(tier),
    kindOfProduct(appliance),
    headOfficeInEU(yes),
    weight(moreThan2T),
    mainResponsabilities(X), var(X).
nextQuestion(mainResponsabilities) :-
    kindOfOrganisation(tier),
    kindOfProduct(part),
    headOfficeInEU(yes),
    weight(moreThan2T),
    standardPart(no),
    mainResponsabilities(X), var(X).

nextQuestion(typeCertificate) :-
    kindOfOrganisation(motorist),
    kindOfProduct(motorOrPropulsionSystem),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(X), var(X).
nextQuestion(requestModification) :-
    kindOfOrganisation(motorist),
    kindOfProduct(motorOrPropulsionSystem),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(no),
    requestModification(X), var(X).
nextQuestion(nextTarget) :-
    kindOfOrganisation(motorist),
    kindOfProduct(motorOrPropulsionSystem),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(no),
    requestModification(no),
    nextTarget(X), var(X).

nextQuestion(typeCertificate) :-
    kindOfOrganisation(oem),
    kindOfProduct(aircraft),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(X), var(X).

nextQuestion(modificationRequestOrNewProgram) :-
    kindOfOrganisation(oem),
    kindOfProduct(aircraft),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(yes),
    modificationRequestOrNewProgram(X), var(X).

nextQuestion(requestModification) :-
    kindOfOrganisation(oem),
    kindOfProduct(aircraft),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(no),
    requestModification(X), var(X).
nextQuestion(nextTarget) :-
    kindOfOrganisation(oem),
    kindOfProduct(aircraft),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(no),
    requestModification(no),
    nextTarget(X), var(X).
nextQuestion(privilegeFromEASA) :-
    kindOfOrganisation(oem),
    kindOfProduct(aircraft),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(no),
    requestModification(no),
    privilegeFromEASA(X), var(X).
nextQuestion(operationalConditionsMightBeRestricted) :-
    kindOfOrganisation(oem),
    kindOfProduct(aircraft),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(no),
    requestModification(no),
    operationalConditionsMightBeRestricted(X), var(X).

scenario(0) :-
    kindOfOrganisation(oem),
    kindOfProduct(X), X \= aircraft.
scenario(0) :-
    kindOfOrganisation(oem),
    kindOfProduct(aircraft),
    headOfficeInEU(no).
scenario(0) :-
    kindOfOrganisation(oem),
    kindOfProduct(aircraft),
    headOfficeInEU(yes),
    weight(lessThan2T).
scenario(0) :-
    kindOfOrganisation(motorist),
    kindOfProduct(X), X \= motorOrPropulsionSystem.
scenario(0) :-
    kindOfOrganisation(motorist),
    kindOfProduct(motorOrPropulsionSystem),
    headOfficeInEU(no).
scenario(0) :-
    kindOfOrganisation(motorist),
    kindOfProduct(motorOrPropulsionSystem),
    headOfficeInEU(yes),
    weight(lessThan2T).
scenario(0) :-
    kindOfOrganisation(tier),
    kindOfProduct(X), X \= appliance.
scenario(0) :-
    kindOfOrganisation(motorist),
    kindOfProduct(appliance),
    headOfficeInEU(no).
scenario(0) :-
    kindOfOrganisation(motorist),
    kindOfProduct(appliance),
    headOfficeInEU(yes),
    weight(lessThan2T).
scenario(0) :-
    kindOfOrganisation(tier),
    kindOfProduct(X), X \= tier.
scenario(0) :-
    kindOfOrganisation(motorist),
    kindOfProduct(tier),
    headOfficeInEU(no).
scenario(0) :-
    kindOfOrganisation(motorist),
    kindOfProduct(tier),
    headOfficeInEU(yes),
    weight(lessThan2T).
scenario(0) :-
    kindOfOrganisation(motorist),
    kindOfProduct(tier),
    headOfficeInEU(yes),
    weight(lessThan2T),
    standardPart(yes).

scenario(5) :-
    kindOfOrganisation(tier),
    kindOfProduct(appliance),
    headOfficeInEU(yes),
    weight(moreThan2T),
    standardPart(no),
    mainResponsabilities(design).
scenario(6) :-
    kindOfOrganisation(tier),
    kindOfProduct(part),
    headOfficeInEU(yes),
    weight(moreThan2T),
    standardPart(no),
    mainResponsabilities(production).
scenario(7) :-
    kindOfOrganisation(tier),
    kindOfProduct(part),
    headOfficeInEU(yes),
    weight(moreThan2T),
    standardPart(no),
    mainResponsabilities(both).

scenario(0) :-
    kindOfOrganisation(motorist),
    kindOfProduct(motorOrPropulsionSystem),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(yes).
scenario(0) :-
    kindOfOrganisation(motorist),
    kindOfProduct(motorOrPropulsionSystem),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(no),
    requestModification(yes).
scenario(0) :-
    kindOfOrganisation(motorist),
    kindOfProduct(motorOrPropulsionSystem),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(no),
    requestModification(no),
    nextTarget(X), X \= tcAndCoA.

scenario(8) :-
    kindOfOrganisation(motorist),
    kindOfProduct(motorOrPropulsionSystem),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(no),
    requestModification(no),
    nextTarget(tcAndCoA).

scenario(0) :-
    kindOfOrganisation(oem),
    kindOfProduct(aircraft),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(yes),
    requestModificationOrNewProgram(newProgram).
scenario(0) :-
    kindOfOrganisation(oem),
    kindOfProduct(aircraft),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(yes),
    modificationRequestOrNewProgram(modificationRequest).

scenario(0) :-
    kindOfOrganisation(oem),
    kindOfProduct(aircraft),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(no),
    requestModification(yes).

scenario(1) :-
    kindOfOrganisation(oem),
    kindOfProduct(aircraft),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(no),
    requestModification(no),
    nextTarget(en9100).
scenario(2) :-
    kindOfOrganisation(oem),
    kindOfProduct(aircraft),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(no),
    requestModification(no),
    nextTarget(designApproval),
    privilegeFromEASA(no),
    operationalConditionsMightBeRestricted(no).
scenario(3) :-
    kindOfOrganisation(oem),
    kindOfProduct(aircraft),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(no),
    requestModification(no),
    nextTarget(productionApproval),
    privilegeFromEASA(no),
    operationalConditionsMightBeRestricted(no).
scenario(4) :-
    kindOfOrganisation(oem),
    kindOfProduct(aircraft),
    headOfficeInEU(yes),
    weight(moreThan2T),
    typeCertificate(no),
    requestModification(no),
    nextTarget(tcAndCoa),
    privilegeFromEASA(no),
    operationalConditionsMightBeRestricted(no).
scenario(0) :-
    nextTarget(designApproval),
    privilegeFromEASA(yes).
scenario(0) :-
    nextTarget(designApproval),
    privilegeFromEASA(no),
    operationalConditionsMightBeRestricted(yes).
scenario(0) :-
    nextTarget(productionApproval),
    privilegeFromEASA(yes).
scenario(0) :-
    nextTarget(productionApproval),
    privilegeFromEASA(no),
    operationalConditionsMightBeRestricted(yes).
scenario(0) :-
    nextTarget(tcAndCoA),
    privilegeFromEASA(yes).
scenario(0) :-
    nextTarget(tcAndCoA),
    privilegeFromEASA(no),
    operationalConditionsMightBeRestricted(yes).

reasoner(X) :- nextQuestion(X).
reasoner(X) :- scenario(X).
