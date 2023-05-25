import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './abstract-activity.reducer';

export const AbstractActivityDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const abstractActivityEntity = useAppSelector(state => state.abstractActivity.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="abstractActivityDetailsHeading">
          <Translate contentKey="eduApp.abstractActivity.detail.title">AbstractActivity</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{abstractActivityEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="eduApp.abstractActivity.title">Title</Translate>
            </span>
          </dt>
          <dd>{abstractActivityEntity.title}</dd>
          <dt>
            <Translate contentKey="eduApp.abstractActivity.goal">Goal</Translate>
          </dt>
          <dd>
            {abstractActivityEntity.goals
              ? abstractActivityEntity.goals.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.title}</a>
                    {abstractActivityEntity.goals && i === abstractActivityEntity.goals.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/abstract-activity" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/abstract-activity/${abstractActivityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AbstractActivityDetail;
