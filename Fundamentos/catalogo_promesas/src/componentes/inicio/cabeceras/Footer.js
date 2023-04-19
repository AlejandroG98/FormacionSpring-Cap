import React, { Component } from 'react'

export default class Footer extends Component {
  render() {
    return (
      <>
      <footer className="text-center text-lg-start bg-primary text-white footer">
        <section className="d-flex justify-content-center justify-content-lg-between p-2 border-bottom"></section>
        <section className="">
          <div className="container text-center text-md-start mt-5">
            <div className="row mt-3">
              <div className="col-md-3 col-lg-4 col-xl-3 mx-auto mb-4">
                <h6 className="text-uppercase fw-bold mb-4">
                  <i className="fas fa-gem me-3">Hola</i>
                </h6>
                <p>Esto es una prueba Front para Catalogo</p>
              </div>
              <div className="col-md-3 col-lg-4 col-xl-3 mx-auto mb-4">
                <h6 className="text-uppercase fw-bold mb-4">
                  <i className="fas fa-gem me-3">Catalogo</i>
                </h6>
                <p className="no-underline">
                  <a className="text-reset">
                    Actors
                  </a>
                </p>
                <p className="no-underline">
                  <a className="text-reset">
                    Category
                  </a>
                </p>
                <p className="no-underline">
                  <a className="text-reset">
                    Film
                  </a>
                </p>
                <p className="no-underline">
                  <a className="text-reset">
                    Language
                  </a>
                </p>
              </div>
              <div className="col-md-3 col-lg-2 col-xl-2 mx-auto mb-4">
                <h6 className="text-uppercase fw-bold mb-4">
                  <i className="fas fa-gem me-3">Otros Links</i>
                </h6>
                <p className="no-underline">
                  <a className="text-reset">
                    No
                  </a>
                </p>
                <p className="no-underline">
                  <a className="text-reset">
                    -
                  </a>
                </p>
                <p className="no-underline">
                  <a className="text-reset">
                    -
                  </a>
                </p>
                <p className="no-underline">
                  <a className="text-reset">
                    -
                  </a>
                </p>
              </div>
              <div className="col-md-4 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4">
                <h6 className="text-uppercase fw-bold mb-4">
                  <i className="fas fa-gem me-3">Contacto</i>
                </h6>
                <p className="no-underline">
                  <i className="fas fa4-github me-3" />
                  <a
                    className="text-reset fw-bold"
                    href="https://github.com/AlejandroG98"
                    target="_onBlank"
                  >
                    GitHub
                  </a>
                </p>
                <p className="no-underline">
                  <i className="fas fa-envelope me-3" />
                  ejemplo@ejemplo.com
                </p>
                <p className="no-underline">
                  <i className="fas fa-phone me-3" />
                  Calle Falsa 123
                </p>
                <p className="no-underline">
                  <i className="fas fa-print me-3" /> + 01 234 567 89
                </p>
              </div>
            </div>
          </div>
        </section>
        <div className="text-center p-4 no-underline">
          © 2023  &nbsp;
          <a className="text-white fw-bold ">Alejandro G</a>
        </div>
      </footer>
      </>
    )
  }
}
