SELECT party1gid.CODE AS 'Person1' , personrelationshiptypegid.CODE AS 'Relation' , party2gid.CODE AS 'Person2'
FROM personrelationship

INNER JOIN person AS person1 ON person1.IDENTIFIER = personrelationship.person1
INNER JOIN party AS party1 ON party1.IDENTIFIER = person1.IDENTIFIER
INNER JOIN globalidentifier AS party1gid ON party1gid.IDENTIFIER = party1.GLOBALIDENTIFIER_IDENTIFIER

INNER JOIN person AS person2 ON person2.IDENTIFIER = personrelationship.person2
INNER JOIN party AS party2 ON party2.IDENTIFIER = person2.IDENTIFIER
INNER JOIN globalidentifier AS party2gid ON party2gid.IDENTIFIER = party2.GLOBALIDENTIFIER_IDENTIFIER

INNER JOIN personrelationshiptype ON personrelationshiptype.IDENTIFIER = personrelationship.`type`
INNER JOIN globalidentifier AS personrelationshiptypegid ON personrelationshiptypegid.IDENTIFIER = personrelationshiptype.GLOBALIDENTIFIER_IDENTIFIER