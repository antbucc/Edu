import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILearnerPreference } from 'app/shared/model/learner-preference.model';
import { getEntities } from './learner-preference.reducer';

export const LearnerPreference = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const learnerPreferenceList = useAppSelector(state => state.learnerPreference.entities);
  const loading = useAppSelector(state => state.learnerPreference.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="learner-preference-heading" data-cy="LearnerPreferenceHeading">
        <Translate contentKey="eduApp.learnerPreference.home.title">Learner Preferences</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.learnerPreference.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/learner-preference/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.learnerPreference.home.createLabel">Create new Learner Preference</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {learnerPreferenceList && learnerPreferenceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.learnerPreference.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.learnerPreference.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.learnerPreference.style">Style</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.learnerPreference.modality">Modality</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.learnerPreference.difficulty">Difficulty</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.learnerPreference.disability">Disability</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.learnerPreference.learner">Learner</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {learnerPreferenceList.map((learnerPreference, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/learner-preference/${learnerPreference.id}`} color="link" size="sm">
                      {learnerPreference.id}
                    </Button>
                  </td>
                  <td>{learnerPreference.title}</td>
                  <td>
                    <Translate contentKey={`eduApp.LearningStyleType.${learnerPreference.style}`} />
                  </td>
                  <td>
                    <Translate contentKey={`eduApp.ModalityType.${learnerPreference.modality}`} />
                  </td>
                  <td>
                    <Translate contentKey={`eduApp.Difficulty.${learnerPreference.difficulty}`} />
                  </td>
                  <td>
                    <Translate contentKey={`eduApp.DisabilityType.${learnerPreference.disability}`} />
                  </td>
                  <td>
                    {learnerPreference.learner ? (
                      <Link to={`/learner/${learnerPreference.learner.id}`}>{learnerPreference.learner.surname}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/learner-preference/${learnerPreference.id}`}
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
                        to={`/learner-preference/${learnerPreference.id}/edit`}
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
                        to={`/learner-preference/${learnerPreference.id}/delete`}
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
              <Translate contentKey="eduApp.learnerPreference.home.notFound">No Learner Preferences found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default LearnerPreference;
