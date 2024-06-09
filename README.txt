------------------------------------------------------------- README --------------------------------------------------------------------
 

- First of all, I make the assumption that the app does not show only these 5 records in the table forever. I approach it as more records can be added or removed from the table.

If the table always showed the same content, I would add another test that would check the content on the table that should always return these standard values in both Functional UI and API tests. Also in that case, I would not use the length property in my tests and the respective validations that are related to it.


-------------------------------------------------------- Functional UI tests ------------------------------------------------------------

- I created a selectors.ts file in support folder in order ro re-use these selectors properly wherever they are needed in my tests.

- The selectors that I picked to locate elements are mostly with class attribgutes. The best practice would be to have an attribute in the elements that would be used only for the automation tests but something like that is not provided here.

- To access the table rows I used the '.root .MuiGrid-container' selector and then I combined it with '.MuiTypography-root' selector so I could access a specific value in the table. One note here is that the '.root .MuiGrid-container' selector locates also the headers row of the table. This is the reason why when I am trying to locate the records of the table, I never locate the first table row element (element[0]). Also, for the headers of the table, I use the '.headers' selector that is unique and easier to use and understand.

- I make the assumption that the desired sorting functionality is to show the records in ascending order regardless of the times that the user clicks a header to sort by the respective column.

- For the sorting of the dates, I have created a functon that handles the comparison for both strings and timestamps. So, for Name column I compare the text of each row with the next one, while for the two date columns, because I want to compare the date that the text represents in each row with the next one, I parse the text value so to compare integer timestamps.

- For the search input, I check the results on the table based on three inputs. One that validates that there is no case sensitivity, one that validates that the space is counted as part of the text and the last one that shows no results. 

- While I ran the test regarding the search input locally and it passed, when I built the CI/CD pipeline the test was failing. So I used cy.intercept() and cy.wait() methods to solve the issue.

- Two of my tests here are failing because there is a bug in the ascending order of Last modified column.


---------------------------------------------------------- API Tests ----------------------------------------------------------------

- As I mentioned earlier I make the assumption that the items are not always these exact five that are returned. If the api always returned these exact items, I would have checked the structure and the values for all of them.

- I have created a function that handles the ascending/descending sorting functionality for the properties that it is implemented.

- In general, in my tests, I am checking the structure of the items, the sorting functionality for each property and I handle the cases that error code statues are returned as expected.




