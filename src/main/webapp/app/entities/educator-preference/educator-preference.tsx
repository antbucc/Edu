import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEducatorPreference } from 'app/shared/model/educator-preference.model';
import { getEntities } from './educator-preference.reducer';

export const EducatorPreference = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const educatorPreferenceList = useAppSelector(state => state.educatorPreference.entities);
  const loading = useAppSelector(state => state.educatorPreference.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="educator-preference-heading" data-cy="EducatorPreferenceHeading">
        <Translate contentKey="eduApp.educatorPreference.home.title">Educator Preferences</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.educatorPreference.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/educator-preference/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.educatorPreference.home.createLabel">Create new Educator Preference</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {educatorPreferenceList && educatorPreferenceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.educatorPreference.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.educatorPreference.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.educatorPreference.style">Style</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.educatorPreference.modality">Modality</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.educatorPreference.difficulty">Difficulty</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.educatorPreference.preferredActivities">Preferred Activities</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.educatorPreference.educator">Educator</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {educatorPreferenceList.map((educatorPreference, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/educator-preference/${educatorPreference.id}`} color="link" size="sm">
                      {educatorPreference.id}
                    </Button>
                  </td>
                  <td>{educatorPreference.title}</td>
                  <td>
                    <Translate contentKey={`eduApp.LearningStyleType.${educatorPreference.style}`} />
                  </td>
                  <td>
                    <Translate contentKey={`eduApp.ModalityType.${educatorPreference.modality}`} />
                  </td>
                  <td>
                    <Translate contentKey={`eduApp.Difficulty.${educatorPreference.difficulty}`} />
                  </td>
                  <td>
                    {educatorPreference.preferredActivities ? (
                      <Link to={`/preferred-activity/${educatorPreference.preferredActivities.id}`}>
                        {educatorPreference.preferredActivities.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {educatorPreference.educator ? (
                      <Link to={`/educator/${educatorPreference.educator.id}`}>{educatorPreference.educator.lastName}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/educator-preference/${educatorPreference.id}`}
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
                        to={`/educator-preference/${educatorPreference.id}/edit`}
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
                        to={`/educator-preference/${educatorPreference.id}/delete`}
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
              <Translate contentKey="eduApp.educatorPreference.home.notFound">No Educator Preferences found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default EducatorPreference;
