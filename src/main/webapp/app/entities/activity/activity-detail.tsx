import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './activity.reducer';

export const ActivityDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const activityEntity = useAppSelector(state => state.activity.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="activityDetailsHeading">
          <Translate contentKey="eduApp.activity.detail.title">Activity</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{activityEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="eduApp.activity.title">Title</Translate>
            </span>
          </dt>
          <dd>{activityEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="eduApp.activity.description">Description</Translate>
            </span>
          </dt>
          <dd>{activityEntity.description}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="eduApp.activity.type">Type</Translate>
            </span>
          </dt>
          <dd>{activityEntity.type}</dd>
          <dt>
            <span id="tool">
              <Translate contentKey="eduApp.activity.tool">Tool</Translate>
            </span>
          </dt>
          <dd>{activityEntity.tool}</dd>
          <dt>
            <span id="difficulty">
              <Translate contentKey="eduApp.activity.difficulty">Difficulty</Translate>
            </span>
          </dt>
          <dd>{activityEntity.difficulty}</dd>
          <dt>
            <Translate contentKey="eduApp.activity.concept">Concept</Translate>
          </dt>
          <dd>
            {activityEntity.concepts
              ? activityEntity.concepts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.title}</a>
                    {activityEntity.concepts && i === activityEntity.concepts.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="eduApp.activity.precondition">Precondition</Translate>
          </dt>
          <dd>
            {activityEntity.preconditions
              ? activityEntity.preconditions.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.title}</a>
                    {activityEntity.preconditions && i === activityEntity.preconditions.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="eduApp.activity.effect">Effect</Translate>
          </dt>
          <dd>
            {activityEntity.effects
              ? activityEntity.effects.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.title}</a>
                    {activityEntity.effects && i === activityEntity.effects.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/activity" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/activity/${activityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActivityDetail;
