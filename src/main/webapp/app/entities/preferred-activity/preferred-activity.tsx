import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPreferredActivity } from 'app/shared/model/preferred-activity.model';
import { getEntities } from './preferred-activity.reducer';

export const PreferredActivity = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const preferredActivityList = useAppSelector(state => state.preferredActivity.entities);
  const loading = useAppSelector(state => state.preferredActivity.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="preferred-activity-heading" data-cy="PreferredActivityHeading">
        <Translate contentKey="eduApp.preferredActivity.home.title">Preferred Activities</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.preferredActivity.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/preferred-activity/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.preferredActivity.home.createLabel">Create new Preferred Activity</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {preferredActivityList && preferredActivityList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.preferredActivity.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.preferredActivity.activity">Activity</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.preferredActivity.educatorPreference">Educator Preference</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {preferredActivityList.map((preferredActivity, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/preferred-activity/${preferredActivity.id}`} color="link" size="sm">
                      {preferredActivity.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`eduApp.ActivityType.${preferredActivity.activity}`} />
                  </td>
                  <td>
                    {preferredActivity.educatorPreference ? (
                      <Link to={`/educator-preference/${preferredActivity.educatorPreference.id}`}>
                        {preferredActivity.educatorPreference.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/preferred-activity/${preferredActivity.id}`}
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
                        to={`/preferred-activity/${preferredActivity.id}/edit`}
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
                        to={`/preferred-activity/${preferredActivity.id}/delete`}
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
              <Translate contentKey="eduApp.preferredActivity.home.notFound">No Preferred Activities found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PreferredActivity;
