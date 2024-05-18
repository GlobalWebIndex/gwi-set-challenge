class MainChartPage {
  // Define elements and actions within the page
  getSearchField() {
    return cy.get('input[aria-invalid="false"]');
  }

  getChartNameFromResultsList() {
    return cy.get('[class="MuiGrid-root MuiGrid-container MuiGrid-align-items-xs-center MuiGrid-justify-content-xs-space-between"]').children('[class="MuiGrid-root MuiGrid-item MuiGrid-grid-xs-6"]');
  }

  getCreateChartButton() {
    return cy.get('span[class="MuiButton-label"]').contains('Create a chart')
  }

  inputTextAndSearchCharts(chartname) {
    this.getSearchField().type(chartname);
  }

  verifySearch(chartname) {
    this.getChartNameFromResultsList().should('have.text', chartname).and('to.have.length', 1)
  }

  clickCreateChartButton() {
    this.getCreateChartButton().click()
  }

}

export default MainChartPage;