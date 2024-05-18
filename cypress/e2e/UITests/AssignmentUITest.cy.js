import mainChartPage from "./pageObjects/MainChartPage.js"
import createChartPage from "./pageObjects/CreateChartPage.js"
const ChartPage = new mainChartPage();
const CreateChart = new createChartPage();


describe('UI assignment test', () => {
  
  beforeEach(() => {
    cy.visit('http://localhost:3000/')
  })
  
  it('Search And Verify Results', () => {
    ChartPage.inputTextAndSearchCharts('Chart 1')
    ChartPage.verifySearch('Chart 1')
  });

  it('Create a Chart', () => {
    ChartPage.clickCreateChartButton(),
    CreateChart.clickGoBackButton()
  });


});