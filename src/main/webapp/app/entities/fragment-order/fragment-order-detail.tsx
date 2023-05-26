import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './fragment-order.reducer';

export const FragmentOrderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fragmentOrderEntity = useAppSelector(state => state.fragmentOrder.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fragmentOrderDetailsHeading">
          <Translate contentKey="eduApp.fragmentOrder.detail.title">FragmentOrder</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fragmentOrderEntity.id}</dd>
          <dt>
            <span id="order">
              <Translate contentKey="eduApp.fragmentOrder.order">Order</Translate>
            </span>
          </dt>
          <dd>{fragmentOrderEntity.order}</dd>
          <dt>
            <Translate contentKey="eduApp.fragmentOrder.fragment">Fragment</Translate>
          </dt>
          <dd>{fragmentOrderEntity.fragment ? fragmentOrderEntity.fragment.id : ''}</dd>
          <dt>
            <Translate contentKey="eduApp.fragmentOrder.set">Set</Translate>
          </dt>
          <dd>{fragmentOrderEntity.set ? fragmentOrderEntity.set.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/fragment-order" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fragment-order/${fragmentOrderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FragmentOrderDetail;
