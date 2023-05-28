import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILearningDisability } from 'app/shared/model/learning-disability.model';
import { getEntities } from './learning-disability.reducer';

export const LearningDisability = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const learningDisabilityList = useAppSelector(state => state.learningDisability.entities);
  const loading = useAppSelector(state => state.learningDisability.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="learning-disability-heading" data-cy="LearningDisabilityHeading">
        <Translate contentKey="eduApp.learningDisability.home.title">Learning Disabilities</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.learningDisability.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/learning-disability/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.learningDisability.home.createLabel">Create new Learning Disability</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {learningDisabilityList && learningDisabilityList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.learningDisability.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.learningDisability.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.learningDisability.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.learningDisability.disabilityType">Disability Type</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.learningDisability.learner">Learner</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {learningDisabilityList.map((learningDisability, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/learning-disability/${learningDisability.id}`} color="link" size="sm">
                      {learningDisability.id}
                    </Button>
                  </td>
                  <td>{learningDisability.name}</td>
                  <td>{learningDisability.description}</td>
                  <td>
                    <Translate contentKey={`eduApp.DisabilityType.${learningDisability.disabilityType}`} />
                  </td>
                  <td>
                    {learningDisability.learner ? (
                      <Link to={`/learner/${learningDisability.learner.id}`}>{learningDisability.learner.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/learning-disability/${learningDisability.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/learning-disability/${learningDisability.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/learning-disability/${learningDisability.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="eduApp.learningDisability.home.notFound">No Learning Disabilities found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default LearningDisability;
