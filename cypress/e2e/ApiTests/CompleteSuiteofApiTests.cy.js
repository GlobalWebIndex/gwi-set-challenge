describe('API /api/charts', () => {
  const baseUrl = 'http://localhost:3000/api/charts';

  it('should return the default list when no query parameters are provided', () => {
    cy.request(baseUrl).then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body).to.have.property('charts');
      expect(response.body.charts).to.be.an('array');
    });
  });

  const testSorting = (orderby, order) => {
    it(`should return sorted list by ${orderby} in ${order} order`, () => {
      cy.request(`${baseUrl}?orderBy=${orderby}&order=${order}`).then((response) => {
        expect(response.status).to.eq(200);
        expect(response.body).to.have.property('charts');
        expect(response.body.charts).to.be.an('array');

        const charts = response.body.charts;
        const compare = (a, b, key) => {
          if (order === 'asc') {
            return a[key] >= b[key] ? 1 : -1;
          } else {
            return a[key] <= b[key] ? 1 : -1;
          }
        };

        for (let i = 1; i < charts.length; i++) {
          if (orderby === 'name') {
            expect(compare(charts[i - 1], charts[i], 'name')).to.be.at.most(0);
          } else if (orderby === 'DateCreated') {
            expect(compare(charts[i - 1], charts[i], 'created_at')).to.be.at.most(0);
          } else if (orderby === 'DateModified') {
            expect(compare(charts[i - 1], charts[i], 'modified_at')).to.be.at.most(0);
          }
        }
      });
    });
  };

  ['name', 'dateModified'].forEach(orderby => {
    ['asc', 'desc'].forEach(order => {
      testSorting(orderby, order);
    });
  });

  ['dateCreated'].forEach(orderby => {
    ['asc'].forEach(order => {
      testSorting(orderby, order);
    });
  });

  it('should return 500 error when ordering by DateCreated in descending order', () => {
    cy.request({
      url: `${baseUrl}?orderBy=dateCreated&order=desc`,
      failOnStatusCode: false,
    }).then((response) => {
      expect(response.status).to.eq(500);
      expect(response.body).to.have.property('error');
      expect(response.body.error).to.eq('Currently no order by dateCreated descending has been implemented');
    });
  });

  it('should return 400 error for invalid orderby parameter', () => {
    cy.request({
      url: `${baseUrl}?orderBy=invalidParameter`,
      failOnStatusCode: false,
    }).then((response) => {
      expect(response.status).to.eq(400);
      expect(response.body).to.have.property('error');
      expect(response.body.error).to.eq('Please check your request parameters');
    });
  });

  it('should return 404 error for invalid orderby parameter', () => {
    cy.request({
      url: `${baseUrl}RandomUrl`,
      failOnStatusCode: false,
    }).then((response) => {
      expect(response.status).to.eq(404);
    });
  });
});