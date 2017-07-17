SELECT IFNULL(partygid.CODE,'') AS 'Person' , IFNULL(electronicmail.ADDRESS,'') AS 'EMail Address'
FROM electronicmail

INNER JOIN contact ON contact.IDENTIFIER = electronicmail.IDENTIFIER
INNER JOIN contactcollection ON contactcollection.IDENTIFIER = contact.COLLECTION_IDENTIFIER
INNER JOIN party ON party.CONTACTCOLLECTION_IDENTIFIER = contactcollection.IDENTIFIER
INNER JOIN globalidentifier AS partygid ON partygid.IDENTIFIER = party.GLOBALIDENTIFIER_IDENTIFIER
INNER JOIN person ON person.IDENTIFIER = party.IDENTIFIER