import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './set-of-fragment.reducer';

export const SetOfFragmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const setOfFragmentEntity = useAppSelector(state => state.setOfFragment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="setOfFragmentDetailsHeading">
          <Translate contentKey="eduApp.setOfFragment.detail.title">SetOfFragment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{setOfFragmentEntity.id}</dd>
          <dt>
            <span id="order">
              <Translate contentKey="eduApp.setOfFragment.order">Order</Translate>
            </span>
          </dt>
          <dd>{setOfFragmentEntity.order}</dd>
          <dt>
            <Translate contentKey="eduApp.setOfFragment.setOf">Set Of</Translate>
          </dt>
          <dd>{setOfFragmentEntity.setOf ? setOfFragmentEntity.setOf.title : ''}</dd>
          <dt>
            <Translate contentKey="eduApp.setOfFragment.fragment">Fragment</Translate>
          </dt>
          <dd>{setOfFragmentEntity.fragment ? setOfFragmentEntity.fragment.title : ''}</dd>
        </dl>
        <Button tag={Link} to="/set-of-fragment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/set-of-fragment/${setOfFragmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SetOfFragmentDetail;
