/// <reference types="Cypress" />

describe('API Testing with Cypress', () => {

    const apiUrl = 'http://localhost:3000/api/charts';

    it('should return an object that contains an array of charts with a status of 200', () => {
        cy.request('GET', apiUrl).then((response) => {
            expect(response.status).to.eq(200);
            expect(response.body).have.property('charts');
            expect(response.body.charts).to.be.an('array');
            expect(response.body.charts.length).to.greaterThan(0);
        })
    })


    it('should return the correct structure of the items', () => {
        cy.request('GET', apiUrl).then((response) => {
            response.body.charts.forEach((item: number) => {
                expect(item).to.have.all.keys('name', 'created_at', 'modified_at');
            })
        })
    })


    it('should return the items sorted by Name in ascending order', () => {
        cy.request('GET', `${apiUrl}?orderBy=name&order=asc`).then((response) => {
            checkSorting(getPropertyValues(response.body.charts, 'name'), true);
        })
    })


    it('should return the items sorted by Name in descending order', () => {
        cy.request('GET', `${apiUrl}?orderBy=name&order=desc`).then((response) => {
            checkSorting(getPropertyValues(response.body.charts, 'name'), false);
        })
    })


    it('should return the items sorted by Creation date in ascending order', () => {
        cy.request('GET', `${apiUrl}?orderBy=dateCreated&order=asc`).then((response) => {
            checkSorting(getPropertyValues(response.body.charts, 'created_at'), true);
        })
    })


    it('should return the items sorted by Last modified date in ascending order', () => {
        cy.request('GET', `${apiUrl}?orderBy=dateModified&order=asc`).then((response) => {
            checkSorting(getPropertyValues(response.body.charts, 'modified_at'), true);
        })
    })


    it('should return the items sorted by Last modified date in descending order', () => {
        cy.request('GET', `${apiUrl}?orderBy=dateModified&order=desc`).then((response) => {
            checkSorting(getPropertyValues(response.body.charts, 'modified_at'), false);
        })
    })


    it('should return status code 500 when requesting the items sorted by Creation date in descending order', () => {
        cy.request({ method: 'GET', url: `${apiUrl}?orderBy=dateCreated&order=desc`, failOnStatusCode: false }).then((response) => {
            expect(response.status).to.eq(500);
            expect(response.body).to.have.property('error', 'Currently no order by dateCreated descending has been implemented');
        })
    })


    it('should handle invalid requests with 400 status code', () => {
        cy.request({ method: 'GET', url: `${apiUrl}?orderBy=invalidValue`, failOnStatusCode: false }).then((response) => {
            expect(response.status).to.eq(400);
            expect(response.body).to.have.property('error', 'Please check your request parameters');
        })
    })


    it('should handle unexisting requests with 404 status codes', () => {
        cy.request({ method: 'GET', url: `${apiUrl}/notExists`, failOnStatusCode: false }).then((response) => {
            expect(response.status).to.eq(404);
        })
    })


})


let getPropertyValues = (array: object[], property: string) => {
    return array.map(item => item[property]);
}


let checkSorting = (items: object[], isAscending: boolean) => {
    if (items.length > 1) {
        if (isAscending) {
            for (let i = 0; i <= (items.length - 2); i++) {
                expect(items[i] <= items[i + 1], `${items[i]} is not lower or equal to ${items[i + 1]}`).to.be.true;
            }
        }
        else {
            for (let i = 0; i <= (items.length - 2); i++) {
                expect(items[i] >= items[i + 1], `${items[i]} is not greater or equal to ${items[i + 1]}`).to.be.true;
            }
        }
    }
    else expect(true, 'There are not enough items to assert the sorting').to.be.false;
}