import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAbstractActivity } from 'app/shared/model/abstract-activity.model';
import { getEntities } from './abstract-activity.reducer';

export const AbstractActivity = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const abstractActivityList = useAppSelector(state => state.abstractActivity.entities);
  const loading = useAppSelector(state => state.abstractActivity.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="abstract-activity-heading" data-cy="AbstractActivityHeading">
        <Translate contentKey="eduApp.abstractActivity.home.title">Abstract Activities</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.abstractActivity.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/abstract-activity/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.abstractActivity.home.createLabel">Create new Abstract Activity</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {abstractActivityList && abstractActivityList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.abstractActivity.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.abstractActivity.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.abstractActivity.goal">Goal</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {abstractActivityList.map((abstractActivity, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/abstract-activity/${abstractActivity.id}`} color="link" size="sm">
                      {abstractActivity.id}
                    </Button>
                  </td>
                  <td>{abstractActivity.title}</td>
                  <td>
                    {abstractActivity.goals
                      ? abstractActivity.goals.map((val, j) => (
                          <span key={j}>
                            <Link to={`/goal/${val.id}`}>{val.title}</Link>
                            {j === abstractActivity.goals.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/abstract-activity/${abstractActivity.id}`}
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
                        to={`/abstract-activity/${abstractActivity.id}/edit`}
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
                        to={`/abstract-activity/${abstractActivity.id}/delete`}
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
              <Translate contentKey="eduApp.abstractActivity.home.notFound">No Abstract Activities found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default AbstractActivity;
