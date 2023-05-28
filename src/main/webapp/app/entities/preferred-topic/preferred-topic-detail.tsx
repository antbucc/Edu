import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './preferred-topic.reducer';

export const PreferredTopicDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const preferredTopicEntity = useAppSelector(state => state.preferredTopic.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="preferredTopicDetailsHeading">
          <Translate contentKey="eduApp.preferredTopic.detail.title">PreferredTopic</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{preferredTopicEntity.id}</dd>
          <dt>
            <span id="topicId">
              <Translate contentKey="eduApp.preferredTopic.topicId">Topic Id</Translate>
            </span>
          </dt>
          <dd>{preferredTopicEntity.topicId}</dd>
          <dt>
            <span id="topic">
              <Translate contentKey="eduApp.preferredTopic.topic">Topic</Translate>
            </span>
          </dt>
          <dd>{preferredTopicEntity.topic}</dd>
          <dt>
            <Translate contentKey="eduApp.preferredTopic.educatorPreference">Educator Preference</Translate>
          </dt>
          <dd>{preferredTopicEntity.educatorPreference ? preferredTopicEntity.educatorPreference.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/preferred-topic" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/preferred-topic/${preferredTopicEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PreferredTopicDetail;
