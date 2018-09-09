README


Template for testing Rest API on base objects (Actor, Phase, etc.)

The following tests/actions create items in an empty database and ensure 
that all of the CRUD operations are functioning.

Test						Result
----						-------
Read All					No Records Returned
Create Object One			See that it is created
Read All					See the one that was just created
Read Object One				See the object that was just created
Update Object One			See that the object was updated
Read Object One				Verify that object was updated
Create Object Two			See that it is created
Read All					See two objects
Read Object Two				See the object that was just created
Delete Object Two			See successful delete
Read All					See only one object

