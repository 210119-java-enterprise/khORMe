# khORMe_p1

khORMe is an annotation based Object-Relational-Mapper in the vein of hibernate.

## How To:
   ### 1) Compile and install Khorme to .m2 folder
   ### 2) Add the dependancy to your POM
        <dependency>
            <groupId>com.revature</groupId>
            <artifactId>khORMe</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
   ### 3) Create a configuration XML:     
        <project>
            <db_properties>
                <url>jdbc:postgresql:[yourdatabase]</url>
                <admin>Username</admin>
                <pw>Passworf</pw>
            </db_properties>
            <tables>
                 <table>
                    <name>path.to your.Class1</name>
                </table>
                <table>
                    <name>path.to your.Class2</name>
                </table>
                <table>
                    <name>path.to your.Class3</name>
                </table>
            </tables>
        </project>
   ### 4) Initialize khORMe in your main, and point to your config xml file
        Initializer init = new Initializer();
        init.initialize("src/main/resources/khORM.xml");
    
## Features:
    Connection pooling
    Create
    Read 
    Update
    Delete


    
