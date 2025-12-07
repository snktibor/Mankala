# Mankala (Mancala) - Java Swing Implementáció

![Java](https://img.shields.io/badge/Java-17-orange)
![Build](https://img.shields.io/badge/Build-Maven-blue)
![License](https://img.shields.io/badge/License-MIT-green)

> [!NOTE]
> **Egyetemi Projekt**
>
> Ez a repository a **Budapesti Műszaki és Gazdaságtudományi Egyetem** *A programozás alapjai 3* (BMEVIIIAB00) tárgyának 2024/25/1 féléves beadandó feladatát tartalmazza.

---

Egy klasszikus stratégiai táblajáték modern, asztali megvalósítása Java nyelven, Swing grafikus felülettel. A projektet a BME programozás házi feladat keretében készítettem.

## 🌍 A Játékról

A **Mancala** (magyarul Mankala) nem csupán egy játék, hanem a táblajátékok egy egész családja. Eredete a távoli múltba vész, sok történész szerint ez **a világ legrégebbi stratégiai játéka**, amelyet még ma is játszanak.

### Érdekességek (Fun Facts) 🏺
* **Eredet:** A játék nyomait megtalálták az ókori Egyiptomban, sőt római kori romoknál is. A neve az arab *naqala* szóból származik, ami annyit tesz: "mozgatni".
* **A "Vetés" játéka:** A játék mechanikája a mezőgazdasági vetést és betakarítást imitálja. A köveket vagy magvakat "elvetjük" a mélyedésekbe.
* **Matematika:** Bár a szabályok egyszerűek, a játék mély matematikai és stratégiai lehetőségeket rejt, gyakran használják a számolási készségek fejlesztésére.

## 🎮 Játékszabályok

A program a klasszikus **Kalah** variáció szabályait követi:

1.  **Kezdés:** A tábla két oldalán 6-6 gödör (pit) található, bennük kövekkel. A két szélen egy-egy nagy gyűjtő (store).
2.  **Lépés:** A játékos felveszi az összes követ az egyik saját gödréből, és az óramutató járásával ellentétes irányban egyesével szétosztja őket a következő gödrökbe (vetés).
3.  **Saját gyűjtő:** Ha a vetés során a saját gyűjtődbe érsz, oda is teszel egy követ. Az ellenfélébe sosem.
4.  **Extra kör:** Ha az utolsó kő a saját gyűjtődbe esik, **újra léphetsz!**
5.  **Rablás:** Ha az utolsó kő a saját térfeleden egy üres gödörbe esik, és a szemközti (ellenfél) gödörben vannak kövek, akkor a saját kövedet és az ellenfél összes szemközti kövét megszerezed ("betakarítod") a gyűjtődbe.
6.  **Vége:** A játék akkor ér véget, ha az egyik játékos összes gödre kiürül. A másik játékos a maradék köveit a saját gyűjtőjébe teszi. A győztes az, akinek több köve van.

## ✨ Funkciók

* **Mentés és Betöltés:** A játék állása (`board.ser`) és a beállítások (`settings.xml`) elmenthetők.
* **Testreszabhatóság:**
    * Állítható játéktér méret (gödrök száma).
    * Kezdő kövek száma.
    * Csapatnevek és színek (háttér és golyó színe) módosítása.
* **Modern UI:** Egyedi rajzolt Swing komponensek (`RoundedButton`, `TablePanel`) árnyékolással és reszponzív elrendezéssel.

## 🚀 Telepítés és Futtatás

A projekt **Maven** alapú, így a függőségek kezelése és a build folyamat automatizált.

### Előfeltételek
* Java Development Kit (JDK) 17 vagy újabb
* Maven

### Buildelés forráskódból

1.  Klónozd a repót:
    ```bash
    git clone [https://github.com/snktibor/mankala.git](https://github.com/snktibor/mankala.git)
    cd mankala
    ```

2.  Fordítsd le a projektet és futtasd a teszteket:
    ```bash
    mvn clean package
    ```

3.  A kész JAR fájl a `target` mappában jön létre. Futtasd az alábbi paranccsal:
    ```bash
    java -jar target/mankala-1.0-SNAPSHOT.jar
    ```

### 📦 Letöltés (Releases)

Nem szeretnél bajlódni a kóddal? A már lefordított, futtatható verziót letöltheted a GitHub **Releases** oldaláról:

👉 **[Ugrás a Letöltésekhez](../../releases)**

## 📚 Dokumentáció

A fejlesztőknek szánt részletes dokumentáció, amely tartalmazza az osztálydiagramokat, a kódszervezést és a tervezési döntéseket, az alábbi linken érhető el:

📖 **[Fejlesztői Dokumentáció (DEVELOPMENT.md)](DEVELOPMENT.md)**
