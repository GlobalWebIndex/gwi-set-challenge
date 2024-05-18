class CreateChartPage {
    // Define elements and actions within the page
    
    getGoBackButton() {
      return cy.get('span[class="MuiButton-label"]').contains('Go back')
    }
  
    clickGoBackButton() {
      this.getGoBackButton().click()
    }
  
  }
  
  export default CreateChartPage;