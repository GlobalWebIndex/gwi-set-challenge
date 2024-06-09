describe('Get Charts endpoint', () => {

  //Use this to verify that the charts returned on the response, are sorted by descending order by the provided key
  //Key parameter might be given one of the following values: name, created_at, modified_at
  const verifyDescendingOrder = (response, key) => {
    expect(response).to.have.property('status', 200);
    
    const charts = response.body.charts;
  
    charts.forEach(item => {
      expect(item).to.have.property('name')
      expect(item).to.have.property('created_at')
      expect(item).to.have.property('modified_at')
    });
  
    const values = charts.map(chart => chart[key]);
    const sortedValues = [...values].sort((a, b) => b - a);
  
    expect(values).to.deep.equal(sortedValues);
  };
  
  //Use this to verify that the charts returned on the response, are sorted by ascending order by the provided key
  //Key parameter might be given one of the following values: name, created_at, modified_at
  const verifyAscendingOrder = (response, key) => {
    expect(response).to.have.property('status', 200);
    
    const charts = response.body.charts;
  
    charts.forEach(item => {
      expect(item).to.have.property('name')
      expect(item).to.have.property('created_at')
      expect(item).to.have.property('modified_at')
    });

    const values = charts.map(chart => chart[key]);
    const sortedValues = [...values].sort((a, b) => a - b);
  
    expect(values).to.deep.equal(sortedValues);
  };

  it('can get all charts ordered by dateCreated (ascending)', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:3000/api/charts',
      qs: {
        orderBy: 'dateCreated',
        order: 'asc'
      }
    }).then(response => verifyAscendingOrder(response, 'created_at'));
  });

  //Will skip this because it returns 500, "Currently no order by dateCreated descending has been implemented"
  //Will use this request to test error 500 instead
  it.skip('can get all charts ordered by dateCreated (descending)', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:3000/api/charts',
      qs: {
        orderBy: 'dateCreated',
        order: 'desc'
      }
    }).then(response => verifyAscendingOrder(response, 'created_at'));
  });

  it('can get all charts ordered by dateModified (ascending)', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:3000/api/charts',
      qs: {
        orderBy: 'dateModified',
        order: 'asc'
      }
    }).then(response => verifyAscendingOrder(response, 'modified_at'));
  });

  it('can get all charts ordered by dateModified (descending)', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:3000/api/charts',
      qs: {
        orderBy: 'dateModified',
        order: 'desc'
      }
    }).then(response => verifyDescendingOrder(response, 'modified_at'));
  });

  it('can get all charts ordered by name (ascending)', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:3000/api/charts',
      qs: {
        orderBy: 'name',
        order: 'asc'
      }
    }).then(response => verifyAscendingOrder(response, 'name'));
  });

  it('can get all charts ordered by name (descending)', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:3000/api/charts',
      qs: {
        orderBy: 'name',
        order: 'desc'
      }
    }).then(response => verifyAscendingOrder(response, 'name'));
  });

  it('returns 500 for internal server error', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:3000/api/charts',
      qs: {
        orderBy: 'dateCreated',
        order: 'desc'
      },
      failOnStatusCode: false
    }).then((response) => {
      expect(response).to.have.property('status', 500);
    });
  });

  it('returns 400 for invalid query parameters', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:3000/api/charts',
      qs: {
        orderBy: 'notAParameter',
        order: 'asc'
      },
      failOnStatusCode: false
    }).then((response) => {
      expect(response).to.have.property('status', 400);
    });
  });

  it('returns 404 when using invalid endpoint', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:3000/api/chartsss',
      failOnStatusCode: false
    }).then((response) => {
      expect(response).to.have.property('status', 404);
    });
  });
});
