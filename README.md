# LabSampleDBAlpha

Database application for saving data from small mammals for laboratory studies. Database stores information of animals (host) and of different samples collected from each host. There is possibility to add information if DNA or RNA has been isolated from the sample. Updating information of hosts or samples is not yet possible.

Tietokantasovellus villien piennisäkkäiden laboratoriotutkimuksia varten. Tietokantaan voi tallentaa tietoja pyydystetyistä eläimistä (taudinkantajien isäntäeläimet, host) ja jokaisesta eläimestä kerätyistä erilaisista näytteistä (sample). Näytteistä voidaan tallentaa tieto siitä, onko näytteistä eristetty DNA ja/tai RNA. Isäntien ja näytteiden tietoja ei ole tällä hetkellä mahdollista päivittää.

---------------------------------------

Length restrictions of data in SQLite-file included (sample.db) and in PostgreSQL (in Heroku) / Tietojen pituusrajoitteita ohessa olevassa SQLite-tiedostossa ja PostgreSQL-tietokannassa Herokuun ladatussa sovelluksessa:
Host, code: 15 characters/merkkiä
Host, species: 31 char.
Host, sex: 7 char.
Host, age_group: 15 char.
Host, capture_site: 255 char.
Sample, code: 15 char.
Sample, host_code: 15 char.
Sample, organ: 15 char.

Database tables / tietokantataulut:

Host((pk) code:String, species:String, sex:String, age_group:String, capture_year:Integer, capture_site:String)
Sample ((pk) code:String, (fk) host_code -> Host, organ:String, dna_isolated:Boolean, rna_isolated:Boolean)
