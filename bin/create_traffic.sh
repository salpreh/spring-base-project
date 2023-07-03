#!/usr/bin/env bash

BASE_URL="http://localhost:8080/api"

function spaceship_api_traffic() {
  local url="$BASE_URL/spaceship"
  local headers="Content-Type: application/json"

  curl -s -H "$headers" "$url"
  curl -s -H "$headers" "$url/1"
  curl -s -H "$headers" "$url/2"
  curl -s -H "$headers" "$url"
}

function faction_api_traffic() {
  local url="$BASE_URL/faction"
  local headers="Content-Type: application/json"

  curl -s -H "$headers" "$url"
  curl -s -H "$headers" "$url/8"
  curl -s -H "$headers" "$url/9"
  curl -s -H "$headers" "$url/10"
  curl -s -H "$headers" "$url"
}

function planet_api_traffic() {
  local url="$BASE_URL/planet"
  local headers="Content-Type: application/json"

  curl -s -H "$headers" "$url"
  curl -s -H "$headers" "$url/1"
  curl -s -H "$headers" "$url/2"
  curl -s -H "$headers" "$url/3"
  curl -s -H "$headers" "$url"
}

function person_api_traffic() {
  local url="$BASE_URL/person"
  local headers="Content-Type: application/json"

  curl -s -H "$headers" "$url"
  curl -s -H "$headers" "$url/1"
  curl -s -H "$headers" "$url/2"
  curl -s -H "$headers" "$url/3"
  curl -s -H "$headers" "$url/4"
  curl -s -H "$headers" "$url/5"
  curl -s -H "$headers" "$url"
}

echo "Calling to APIs..."

spaceship_api_traffic
faction_api_traffic
planet_api_traffic
person_api_traffic

echo "Done!"
