import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPreferredModality } from 'app/shared/model/preferred-modality.model';
import { getEntities } from './preferred-modality.reducer';

export const PreferredModality = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const preferredModalityList = useAppSelector(state => state.preferredModality.entities);
  const loading = useAppSelector(state => state.preferredModality.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="preferred-modality-heading" data-cy="PreferredModalityHeading">
        <Translate contentKey="eduApp.preferredModality.home.title">Preferred Modalities</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.preferredModality.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/preferred-modality/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.preferredModality.home.createLabel">Create new Preferred Modality</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {preferredModalityList && preferredModalityList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.preferredModality.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.preferredModality.modality">Modality</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.preferredModality.educatorPreference">Educator Preference</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {preferredModalityList.map((preferredModality, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/preferred-modality/${preferredModality.id}`} color="link" size="sm">
                      {preferredModality.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`eduApp.ModalityType.${preferredModality.modality}`} />
                  </td>
                  <td>
                    {preferredModality.educatorPreference ? (
                      <Link to={`/educator-preference/${preferredModality.educatorPreference.id}`}>
                        {preferredModality.educatorPreference.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/preferred-modality/${preferredModality.id}`}
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
                        to={`/preferred-modality/${preferredModality.id}/edit`}
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
                        to={`/preferred-modality/${preferredModality.id}/delete`}
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
              <Translate contentKey="eduApp.preferredModality.home.notFound">No Preferred Modalities found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PreferredModality;
