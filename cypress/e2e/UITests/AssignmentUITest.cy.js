import mainChartPage from "../UITests/pageObjects/MainChartPage.js"
const ChartPage = new mainChartPage();

describe('UI assignment test', () => {
  
  beforeEach(() => {
    cy.visit('http://localhost:3000/')
  })
  
  it('should return the default list when no query parameters are provided', () => {
    ChartPage.inputTextAndSearchCharts()
  });

});