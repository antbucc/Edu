import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISetOfFragment } from 'app/shared/model/set-of-fragment.model';
import { getEntities } from './set-of-fragment.reducer';

export const SetOfFragment = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const setOfFragmentList = useAppSelector(state => state.setOfFragment.entities);
  const loading = useAppSelector(state => state.setOfFragment.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="set-of-fragment-heading" data-cy="SetOfFragmentHeading">
        <Translate contentKey="eduApp.setOfFragment.home.title">Set Of Fragments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.setOfFragment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/set-of-fragment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.setOfFragment.home.createLabel">Create new Set Of Fragment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {setOfFragmentList && setOfFragmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.setOfFragment.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.setOfFragment.order">Order</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.setOfFragment.setOf">Set Of</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.setOfFragment.fragment">Fragment</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {setOfFragmentList.map((setOfFragment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/set-of-fragment/${setOfFragment.id}`} color="link" size="sm">
                      {setOfFragment.id}
                    </Button>
                  </td>
                  <td>{setOfFragment.order}</td>
                  <td>{setOfFragment.setOf ? <Link to={`/set-of/${setOfFragment.setOf.id}`}>{setOfFragment.setOf.title}</Link> : ''}</td>
                  <td>
                    {setOfFragment.fragment ? (
                      <Link to={`/fragment/${setOfFragment.fragment.id}`}>{setOfFragment.fragment.title}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/set-of-fragment/${setOfFragment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/set-of-fragment/${setOfFragment.id}/edit`}
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
                        to={`/set-of-fragment/${setOfFragment.id}/delete`}
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
              <Translate contentKey="eduApp.setOfFragment.home.notFound">No Set Of Fragments found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SetOfFragment;
