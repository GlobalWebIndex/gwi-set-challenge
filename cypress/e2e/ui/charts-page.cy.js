before(() => {
  cy.visit('http://localhost:3000')
})

describe('Charts page', () => {

  it('shows Charts page', () => {
    cy.get('h5').contains('Charts').should('be.visible')
  });

  it('shows all charts', () => {
    cy.get('p').contains('Chart 1').should('be.visible')
    cy.get('p').contains('Chart 2').should('be.visible')
    cy.get('p').contains('Chart 5').should('be.visible')
    cy.get('p').contains('My awesome test 4').should('be.visible')
    cy.get('p').contains('Test 3').should('be.visible')
  });

  it('shows Name column', () => {
    cy.get('button').contains('Name').should('be.visible')
  });

  it('shows Date created column', () => {
    cy.get('button').contains('Date created').should('be.visible')
  });

  it('shows Last modified column', () => {
    cy.get('button').contains('Last modified').should('be.visible')
  });

  it('shows Create a chart button', () => {
    cy.get('button').contains('Create a chart').should('be.visible')
  });
})

describe('Search', () => {
  it('can search for a chart', () => {
    cy.get('input[placeholder="Search charts"]').should('be.visible')
      .type('Chart 1')
    cy.get('p').contains('Chart 1').should('be.visible')
    cy.get('p').contains('Chart 2').should('not.exist')
    cy.get('p').contains('Chart 5').should('not.exist')
    cy.get('p').contains('My awesome test 4').should('not.exist')
    cy.get('p').contains('Test 3').should('not.exist')
  });

  after(() => {
    cy.get('input[placeholder="Search charts"]').clear()
  })
})

describe('Sorting', () => {
  it('can sort by Name', () => {
    cy.get('button').contains('Name').click()
    cy.get('.root > :nth-child(2)').should('contain', 'Chart 1')
    cy.get('.root > :nth-child(3)').should('contain', 'Chart 2')
    cy.get('.root > :nth-child(4)').should('contain', 'Chart 5')
    cy.get('.root > :nth-child(5)').should('contain', 'My awesome test 4')
    cy.get('.root > :nth-child(6)').should('contain', 'Test 3')
  });

  it('can sort by Date Created', () => {
    cy.get('button').contains('Date created').click()
    cy.get('.root > :nth-child(2)').should('contain', 'Chart 2')
    cy.get('.root > :nth-child(3)').should('contain', 'Chart 5')
    cy.get('.root > :nth-child(4)').should('contain', 'My awesome test 4')
    cy.get('.root > :nth-child(5)').should('contain', 'Test 3')
    cy.get('.root > :nth-child(6)').should('contain', 'Chart 1')
  });

  it('can sort by Last modified date', () => {
    cy.get('button').contains('Last modified').click()
    cy.get('.root > :nth-child(2)').should('contain', 'Chart 5')
    cy.get('.root > :nth-child(3)').should('contain', 'My awesome test 4')
    cy.get('.root > :nth-child(4)').should('contain', 'Test 3')
    cy.get('.root > :nth-child(5)').should('contain', 'Chart 2')
    cy.get('.root > :nth-child(6)').should('contain', 'Chart 1')
  });
})