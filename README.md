# khORMe_p1

<h1>khORMe</h1>
khORMe is an annotation based Object-Relational-Mapper in the vein of hibernate.


<h2>How To<h2>
    <h3>Compile and install Khorme to .m2 folder</h3>
    
    <h3>Add the dependancy to your POM</h3>
           <dependency>
            <groupId>com.revature</groupId>
            <artifactId>khORMe</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        
        
        <h3>Create a configuration XML:</h3>
        
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
        
        
        
        
        <h3>Initialize khORMe in your main, and point to your config xml file</h3>
        Initializer init = new Initializer();
        init.initialize("src/main/resources/khORM.xml");
    
    add the required dependency below to your projects POM file
    create a configuration file to point towards your database like the example shown
    create a mapping file for any objects you wish to map to a given table in your database
    add the required setup lines in your code and use as shown below!



<h2>Features:</h2>

    Connection pooling
    Create
    Read 
    Update
    Delete


    
