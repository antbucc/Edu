import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISequenceFragment } from 'app/shared/model/sequence-fragment.model';
import { getEntities } from './sequence-fragment.reducer';

export const SequenceFragment = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const sequenceFragmentList = useAppSelector(state => state.sequenceFragment.entities);
  const loading = useAppSelector(state => state.sequenceFragment.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="sequence-fragment-heading" data-cy="SequenceFragmentHeading">
        <Translate contentKey="eduApp.sequenceFragment.home.title">Sequence Fragments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eduApp.sequenceFragment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/sequence-fragment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eduApp.sequenceFragment.home.createLabel">Create new Sequence Fragment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {sequenceFragmentList && sequenceFragmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eduApp.sequenceFragment.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.sequenceFragment.order">Order</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.sequenceFragment.sequence">Sequence</Translate>
                </th>
                <th>
                  <Translate contentKey="eduApp.sequenceFragment.fragment">Fragment</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {sequenceFragmentList.map((sequenceFragment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/sequence-fragment/${sequenceFragment.id}`} color="link" size="sm">
                      {sequenceFragment.id}
                    </Button>
                  </td>
                  <td>{sequenceFragment.order}</td>
                  <td>
                    {sequenceFragment.sequence ? (
                      <Link to={`/sequence/${sequenceFragment.sequence.id}`}>{sequenceFragment.sequence.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {sequenceFragment.fragment ? (
                      <Link to={`/fragment/${sequenceFragment.fragment.id}`}>{sequenceFragment.fragment.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/sequence-fragment/${sequenceFragment.id}`}
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
                        to={`/sequence-fragment/${sequenceFragment.id}/edit`}
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
                        to={`/sequence-fragment/${sequenceFragment.id}/delete`}
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
              <Translate contentKey="eduApp.sequenceFragment.home.notFound">No Sequence Fragments found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SequenceFragment;
