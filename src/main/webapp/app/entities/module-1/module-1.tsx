import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IModule1 } from 'app/shared/model/module-1.model';
import { getEntities } from './module-1.reducer';

export const Module1 = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const module1List = useAppSelector(state => state.module1.entities);
  const loading = useAppSelector(state => state.module1.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="module-1-heading" data-cy="Module1Heading">
        <Translate contentKey="eduApp.module1.home.title">Module 1 S</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.module1.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/module-1/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.module1.home.createLabel">Create new Module 1</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {module1List && module1List.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.module1.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.module1.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.module1.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.module1.startDate">Start Date</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.module1.endData">End Data</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.module1.level">Level</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.module1.scenario">Scenario</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.module1.fragment">Fragment</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {module1List.map((module1, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/module-1/${module1.id}`} color="link" size="sm">
                      {module1.id}
                    </Button>
                  </td>
                  <td>{module1.title}</td>
                  <td>{module1.description}</td>
                  <td>{module1.startDate ? <TextFormat type="date" value={module1.startDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{module1.endData ? <TextFormat type="date" value={module1.endData} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    <Translate contentKey={`eduApp.Level.${module1.level}`} />
                  </td>
                  <td>{module1.scenario ? <Link to={`/scenario/${module1.scenario.id}`}>{module1.scenario.title}</Link> : ''}</td>
                  <td>
                    {module1.fragments
                      ? module1.fragments.map((val, j) => (
                          <span key={j}>
                            <Link to={`/fragment/${val.id}`}>{val.title}</Link>
                            {j === module1.fragments.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/module-1/${module1.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/module-1/${module1.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/module-1/${module1.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="eduApp.module1.home.notFound">No Module 1 S found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Module1;
