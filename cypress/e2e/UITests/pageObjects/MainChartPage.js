class MainChartPage {
    // Define elements and actions within the page
    getSearchField() {
      return cy.get('input[aria-invalid="false"]');
    }
  
    inputTextAndSearchCharts(chartname) {
      this.getUsernameField().type(chartname);
    }
  }
  
  export default MainChartPage;