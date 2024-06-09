/// <reference types="Cypress" />

import { SELECTORS } from "../support/selectors";

describe('E2E Test Suite for My Charts app', () => {

  beforeEach(() => {
    cy.visit('http://localhost:3000');
  })


  it('should check "Create a chart" button\' UI and that it leads to Page2', () => {

    cy.get(SELECTORS.createChartButton).should('have.text', 'Create a chart');
    cy.get(SELECTORS.createChartButton).should('have.css', 'color', 'rgb(255, 255, 255)');
    cy.get(SELECTORS.createChartButton).should('have.css', 'background-color', 'rgb(222, 27, 118)');
    cy.get(SELECTORS.createChartButton).click();
    cy.url().should('include', '/Page2');

  })


  it('should check the asceding sorting by Name', () => {

    cy.get(SELECTORS.headerButton).eq(0).should('have.text', 'Name');

    cy.get(SELECTORS.tableRows).its('length').should('be.gt', 1).then((length) => {
      checkAscendingSorting(0, false, length);
    })

  })


  it('should check the ascending sorting by Date created', () => {

    cy.get(SELECTORS.headerButton).eq(1).should('have.text', 'Date created');

    cy.get(SELECTORS.tableRows).its('length').should('be.gt', 1).then((length) => {
      checkAscendingSorting(1, true, length);
    })

  })


  it('should check the ascending sorting by Last modified', () => {

    cy.get(SELECTORS.headerButton).eq(2).should('have.text', 'Last modified')

    cy.get(SELECTORS.tableRows).its('length').should('be.gt', 1).then((length) => {
      checkAscendingSorting(2, true, length);
    })

  })


  it('should check Search field\'s initial state and functionality', () => {

    cy.get(SELECTORS.searchInput).should('have.attr', 'placeholder', 'Search charts');
    cy.get(SELECTORS.searchInput).should('have.value', '');

    //check that correct table records are returned
    ['ch', 'T 2'].forEach(checkSearchInputThatReturnsTableRecords);

    //check that no table records are returned
    checkSearchInputWithNoTableRecords('This chart does not exist');

  })


  it('should check compatibility bewtween Search and Sorting functionalities', () => {

    typeSearchInput('chart');
    cy.get(SELECTORS.tableRows).its('length').should('be.gt', 1).then((length) => {
      checkAscendingSorting(0, false, length);
      [1, 2].forEach((elNumber) => {
        checkAscendingSorting(elNumber, true, length);
      })
    })

  })


  afterEach(() => {

    cy.clearCookies();
    cy.clearLocalStorage();

  })



  let checkAscendingSorting = (elNumber: number, isDate: boolean, length: number) => {

    let value1: any;
    let value2: any;

    if (length > 2) {
      //checking two times two ensure that the sorting remains the same regardless of the clicks on the button
      for (let i = 1; i <= 2; i++) {
        cy.get(SELECTORS.headerButton).eq(elNumber).click().should('have.css', 'font-weight', '700');

        for (let j = 1; j <= (length - 2); j++) {
          cy.get(SELECTORS.tableRows).eq(j).find(SELECTORS.typographyItem).eq(elNumber).invoke('text').then((text) => {
            if (isDate) value1 = Date.parse(text);
            else value1 = text;
          })

          cy.get(SELECTORS.tableRows).eq(j + 1).find(SELECTORS.typographyItem).eq(elNumber).invoke('text').then((text) => {
            if (isDate) value2 = Date.parse(text);
            else value2 = text;
          })

          cy.then(() => {
            expect(value1 <= value2, `${value1} is not lower or equal to ${value2}`).to.be.true;
          })
        }
      }
    }
    else expect(true, 'There are not enough records to assert the sorting').to.be.false;
  }


  let checkSearchInputThatReturnsTableRecords = (searchText: string) => {

    typeSearchInput(searchText);

    cy.get(SELECTORS.tableRows).its('length').should('be.gt', 1).then((length) => {

      for (let i = 1; i <= (length - 1); i++) {
        cy.get(SELECTORS.tableRows).eq(i).find(SELECTORS.typographyItem).eq(0).invoke('text').then((text) => {
          expect(text.toLowerCase()).to.contain(searchText.toLowerCase());
        })
      }
      cy.get(SELECTORS.searchInput).clear();
    })
  }


  let checkSearchInputWithNoTableRecords = (searchText: string) => {

    typeSearchInput(searchText);

    cy.get(SELECTORS.tableRows).should('have.length', '1');
    cy.get(SELECTORS.searchInput).clear();

  }


  let typeSearchInput = (searchText: string) => {

    cy.intercept('GET', '/api/charts').as('getCharts');
    cy.get(SELECTORS.searchInput).type(searchText);
    cy.wait('@getCharts');

  }



})